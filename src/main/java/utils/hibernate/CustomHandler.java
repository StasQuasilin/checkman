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
            StringBuilder builder = new StringBuilder();
            List<Seal> seals = hibernator.query(Seal.class, "batch", batch);
            int i = 0;
            for (Seal seal : seals){
                if (seal.getTransportation() == null){
                    free++;
                }
                if (i == 0) {
                    builder.append(seal.getNumber()).append(" ... ");
                } else if (i == seals.size() - 1){
                    builder.append(seal.getNumber());
                }
                i++;
            }
            batch.setTitle(builder.toString());
            batch.setFree(free);
            batch.setTotal(seals.size());
            batch.setArchive(free == 0);
            hibernator.save(batch);
        }
        HibernateSessionFactory.shutdown();
    }
}
