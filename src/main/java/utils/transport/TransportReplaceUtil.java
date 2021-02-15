package utils.transport;

import entity.transport.DocumentNote;
import entity.transport.Transportation;
import org.apache.log4j.Logger;
import utils.DateUtil;
import utils.LanguageBase;
import utils.UpdateUtil;
import utils.hibernate.DateContainers.LT;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;

import javax.swing.*;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by szpt_user045 on 03.11.2019.
 */
public class TransportReplaceUtil {

    private static final String NOTE_AUTO_REPLACE = "note.auto.replace";
    private final dbDAO dao = dbDAOService.getDAO();
    private final UpdateUtil updateUtil = new UpdateUtil();
    private final Logger log = Logger.getLogger(TransportReplaceUtil.class);

    public TransportReplaceUtil() {
        init();
    }

    public void init(){
        checkTransport();
    }
    final LocalTime targetTime = LocalTime.of(0, 5);
    private void initTimer(){
        if (timer != null && timer.isRunning()){
            timer.stop();
        }
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime target = LocalDateTime.of(now.plusDays(1).toLocalDate(), targetTime);
        log.info("Next replace at : " + target.toString());
        long step = target.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() -
                now.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        timer = new Timer((int) step, e -> checkTransport());
        timer.start();

    }

    Timer timer;
    private final LanguageBase lb = LanguageBase.getBase();
    HashMap<String, Object> param = new HashMap<>();
    synchronized void checkTransport(){
        Date now = Date.valueOf(LocalDate.now());
        param.clear();
        param.put("date", new LT(now));
        param.put("archive", false);
        param.put("done", false);

        for (Transportation transportation : dao.getObjectsByParams(Transportation.class, param)) {
            ArrayList<DocumentNote> notes = new ArrayList<>(transportation.getNotes());
            for (DocumentNote n : notes) {
                if (n.getCreator() == null) {
                    dao.remove(n);
                    transportation.getNotes().remove(n);
                }
            }
            DocumentNote note = new DocumentNote();
            note.setTime(Timestamp.valueOf(LocalDateTime.now()));
            note.setTransportation(transportation);
            note.setDocument(transportation.getUid());
            note.setNote(String.format(lb.get(NOTE_AUTO_REPLACE), DateUtil.prettyDate(transportation.getDate())));
            dao.save(note);

            transportation.getNotes().add(note);
            transportation.setDate(now);

            dao.save(transportation);
            log.info("Transportation " + transportation.getId() + " replaced at " + now);
            try {
                updateUtil.onSave(transportation);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        initTimer();
    }

    public void shutdown(){
        timer.stop();
    }
}
