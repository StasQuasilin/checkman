package utils.db.dao.reports;

import entity.reports.Report;
import entity.user.User;

import java.util.List;

public interface ReportDAO {
    List<Report> getReports(User user);

    Report getReportById(Object id);
}
