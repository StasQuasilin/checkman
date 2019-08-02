package utils;

import entity.seals.Seal;
import entity.seals.SealBatch;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;

import java.util.List;

/**
 * Created by Kvasik on 01.07.2019.
 */
public class SealsUtil {

    private static dbDAO dao = dbDAOService.getDAO();

    public static void checkBatch(List<SealBatch> batches){
        batches.forEach(SealsUtil::checkBatch);
    }

    public static synchronized void checkBatch(SealBatch batch){
        List<Seal> seals = dao.getSealsByBatch(batch);
        int free = 0;
        for (Seal seal : seals){
            if (seal.getCargo() == null) {
                free ++;
            }
        }
        batch.setFree(free);
        batch.setTotal(seals.size());
        batch.setArchive(free == 0);
        dao.save(batch);
    }
}
