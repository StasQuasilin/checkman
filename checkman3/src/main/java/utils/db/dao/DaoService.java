package utils.db.dao;

import utils.db.dao.workers.WorkerDAO;
import utils.db.dao.workers.WorkerDAOHibernate;

public final class DaoService {

    private static final WorkerDAO workerDAO = new WorkerDAOHibernate();

    public static WorkerDAO getWorkerDAO() {
        return workerDAO;
    }
}
