package utils.db.dao;

import utils.db.dao.deals.DealDAO;
import utils.db.dao.deals.DealDAOHibernate;
import utils.db.dao.references.OrganisationDAO;
import utils.db.dao.references.OrganisationDAOHibernate;
import utils.db.dao.references.ProductDAO;
import utils.db.dao.references.ProductDAOHibernate;
import utils.db.dao.workers.WorkerDAO;
import utils.db.dao.workers.WorkerDAOHibernate;

public final class DaoService {

    private static final WorkerDAO workerDAO = new WorkerDAOHibernate();
    private static final DealDAO dealDAO = new DealDAOHibernate();
    private static final ProductDAO productDAO = new ProductDAOHibernate();
    private static final OrganisationDAO organisationDAO = new OrganisationDAOHibernate();

    public static WorkerDAO getWorkerDAO() {
        return workerDAO;
    }

    public static DealDAO getDealDAO() {
        return dealDAO;
    }

    public static ProductDAO getProductDAO() {
        return productDAO;
    }

    public static OrganisationDAO getOrganisationDAO() {
        return organisationDAO;
    }
}
