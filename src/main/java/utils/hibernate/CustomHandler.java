package utils.hibernate;

import entity.seals.Seal;
import entity.seals.SealBatch;

import java.util.List;

/**
 * Created by szpt_user045 on 01.07.2019.
 */
public class CustomHandler {
    public static void main(String[] args) {
        Hibernator hibernator = Hibernator.getInstance();
        for (SealBatch batch : hibernator.query(SealBatch.class, null)){
            int free = 0;
            for (Seal seal : hibernator.query(Seal.class, "batch", batch)){
                if (seal.getTransportation() == null){
                    free++;
                }
            }
            batch.setFree(free);
            batch.setArchive(free == 0);
            hibernator.save(batch);
        }
        HibernateSessionFactory.shutdown();
    }
}
