package utils.hibernate.dao;

import api.socket.SubscribeType;
import api.socket.UpdateUtil;
import constants.Keys;
import entity.Report;
import utils.hibernate.Hibernator;

import java.util.List;

public class ReportDAO {
    private final Hibernator hibernator = Hibernator.getInstance();
    private final UpdateUtil updateUtil = new UpdateUtil();

    public Report getReportByUUID(Object id){
        return hibernator.get(Report.class, Keys.UUID, id);
    }

    public void save(Report report) {
        hibernator.save(report);
        updateUtil.update(SubscribeType.reports, report.toJson());
    }

    public List<Report> getReports() {
        return hibernator.query(Report.class, null);
    }
}
