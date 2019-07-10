package utils.hibernate;

import entity.seals.Seal;
import entity.seals.SealBatch;
import entity.transport.ActionTime;
import entity.transport.Transportation;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by szpt_user045 on 01.07.2019.
 */
public class CustomHandler {
    public static void main(String[] args) {
        Hibernator hibernator = Hibernator.getInstance();
        for (Transportation transportation : hibernator.query(Transportation.class, null)){
            ActionTime timeIn = transportation.getTimeIn();
            ActionTime timeOut = transportation.getTimeOut();
            if (timeIn != null && timeOut != null) {
                Timestamp timeInTime = timeIn.getTime();
                Timestamp timeOutTime = timeOut.getTime();

                if (timeInTime.after(timeOutTime)){
                    transportation.setArchivation(timeInTime);
                } else {
                    transportation.setArchivation(timeOutTime);
                }
                hibernator.save(transportation);
            }

        }
        HibernateSessionFactory.shutdown();
    }
}
