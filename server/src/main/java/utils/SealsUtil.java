package utils;

import entity.seals.Seal;
import entity.seals.SealBatch;
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
}
