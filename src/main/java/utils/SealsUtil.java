package utils;

import entity.seals.Seal;
import entity.seals.SealBatch;
import entity.transport.Transportation;
import utils.hibernate.dao.SealDAO;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;

import java.io.IOException;
import java.util.List;

/**
 * Created by Kvasik on 01.07.2019.
 */
public class SealsUtil {

    private static dbDAO dao = dbDAOService.getDAO();
    private UpdateUtil updateUtil = new UpdateUtil();

    public synchronized void checkBatch(SealBatch batch){
        List<Seal> seals = dao.getSealsByBatch(batch);
        int free = 0;
        for (Seal seal : seals){
            if (seal.getCargo() == null) {
                free ++;
            }
        }
        batch.setFree(free);
        batch.setTotal(seals.size());

        if (batch.getTotal() == 0){
            dao.remove(batch);
            updateUtil.onRemove(batch);
        } else {
            batch.setArchive(free == 0);
            dao.save(batch);
        }
        updateUtil.onSave(batch);
    }

    public void removeBatch(SealBatch batch) {
        final List<Seal> seals = dao.getSealsByBatch(batch);
        for (Seal seal : seals){
            if (seal.getCargo() == null){
                dao.remove(seal);
            }
        }
        checkBatch(batch);
    }


}
