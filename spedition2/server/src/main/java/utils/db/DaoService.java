package utils.db;

import utils.db.dao.reports.ReportDAO;
import utils.db.dao.reports.ReportDAOHibernate;
import utils.db.dao.user.UserDAO;
import utils.db.dao.user.UserDAOImpl;

/**
 * Created by DELL on 07.07.2020.
 */
public class DaoService {
    private static final UserDAO userDAO = new UserDAOImpl();
    private static final ReportDAO reportDAO = new ReportDAOHibernate();

    public static UserDAO getUserDAO() {
        return userDAO;
    }

    public static ReportDAO getReportDAO() {
        return reportDAO;
    }
}
