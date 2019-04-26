package utils;

import entity.Worker;
import entity.log.Change;
import entity.log.ChangeLog;
import utils.hibernate.Hibernator;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by szpt_user045 on 12.03.2019.
 */
public class ChangeLogUtil {
    private static final Hibernator hibernator = Hibernator.getInstance();

    public static synchronized void writeLog(String document, String label, Worker creator, List<Change> changes){
        if (changes.size() > 0) {
            ChangeLog log = new ChangeLog();
            log.setDocument(document);
            log.setLabel(label);
            log.setTime(new Timestamp(System.currentTimeMillis()));
            log.setCreator(creator);
            hibernator.save(log);
            for (Change change : changes) {
                change.setLog(log);
                hibernator.save(change);
            }
            changes.clear();
        }
    }
}
