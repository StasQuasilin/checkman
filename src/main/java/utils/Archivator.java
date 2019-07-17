package utils;

import entity.ArchivatorData;
import entity.documents.Deal;
import entity.transport.Transportation;
import org.apache.log4j.Logger;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;

import javax.swing.*;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Created by szpt_user045 on 17.07.2019.
 */
public final class Archivator {

    final static Logger log = Logger.getLogger(Archivator.class);
    final static dbDAO dao = dbDAOService.getDAO();
    final static ArrayList<ArchivatorData> data = new ArrayList<>();
    final static UpdateUtil updateUtil = new UpdateUtil();

    public static void init(){
        log.info("Init archivator");
        data.addAll(dao.getArchivatorData().stream().collect(Collectors.toList()));
        check();
        next();
    }

    private static void check() {
        final LocalDateTime now = LocalDateTime.now();
        final ArrayList<ArchivatorData> toClose = new ArrayList<>();
        data.stream().filter(d -> now.isAfter(d.getTime().toLocalDateTime())).forEach(toClose::add);
        toClose.forEach(Archivator::close);
        toClose.clear();

    }
    static Timer timer;
    final static long HOUR = 60 * 60 * 1000;
    private static void next() {
        timer = new Timer((int) HOUR, e -> {
            check();
        });
        timer.start();
    }

    private static void close(ArchivatorData d) {
        switch (d.getType()){
            case deal:
                Deal deal = dao.getDealById(d.getDocument());
                deal.setArchive(true);
                dao.saveDeal(deal);

                try {
                    updateUtil.onRemove(deal);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                log.info("Archive deal " + deal.getId());
                break;
            case transportation:
                Transportation transportation = dao.getTransportationById(d.getDocument());
                transportation.setArchive(true);
                dao.saveTransportation(transportation);
                try {
                    updateUtil.onRemove(transportation);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                log.info("Archive transportation " + transportation.getId());
                break;
        }
        data.remove(d);
        dao.remove(d);
    }

    public static void add(ArchivatorType type, int document){
        ArchivatorData d = new ArchivatorData();
        d.setType(type);
        d.setDocument(document);
        d.setTime(Timestamp.valueOf(LocalDateTime.now().plusDays(1)));
        dao.save(d);
        data.add(d);
    }

    public static void add(Deal deal){
        log.info("Add deal " + deal.getId());
        add(ArchivatorType.deal, deal.getId());
    }

    public static void add(Transportation transportation){
        log.info("Add transportation " + transportation.getId());
        add(ArchivatorType.transportation, transportation.getId());
    }

    public static void stop(){
        timer.stop();
    }
}
