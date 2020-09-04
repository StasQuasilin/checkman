package utils.db.dao;

import utils.db.dao.deals.DealDAO;
import utils.db.dao.deals.DealDAOHibernate;
import utils.db.dao.workers.WorkerDAO;
import utils.db.dao.workers.WorkerDAOHibernate;

public final class DaoService {

    private static final WorkerDAO workerDAO = new WorkerDAOHibernate();
    private static final DealDAO dealDAO = new DealDAOHibernate();

    public static WorkerDAO getWorkerDAO() {
        return workerDAO;
    }

    public static DealDAO getDealDAO() {
        return dealDAO;
    }
}
