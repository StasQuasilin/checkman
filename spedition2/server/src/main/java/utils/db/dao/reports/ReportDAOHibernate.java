package utils.db.dao.reports;

import entity.reports.Report;
import entity.user.User;
import utils.db.hibernate.Hibernator;
import utils.db.hibernate.State;

import java.util.LinkedList;
import java.util.List;

public class ReportDAOHibernate implements ReportDAO {

    private final Hibernator hibernator = Hibernator.getInstance();

    @Override
    public List<Report> getReports(User user) {
        final LinkedList<Report> reports = new LinkedList<>();
        reports.addAll(hibernator.query(Report.class, "owner", user, 10));
        reports.addAll(hibernator.query(Report.class, "owner", State.isNull));
        return reports;
    }
}
