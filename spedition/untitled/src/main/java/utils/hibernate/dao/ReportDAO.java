package utils.hibernate.dao;

import api.socket.SubscribeType;
import api.socket.UpdateUtil;
import constants.Keys;
import entity.Expense;
import entity.Report;
import entity.ReportField;
import utils.hibernate.Hibernator;

import java.util.List;

import static constants.Keys.REPORT;

public class ReportDAO {
    private final Hibernator hibernator = Hibernator.getInstance();
    private final UpdateUtil updateUtil = new UpdateUtil();

    public Report getReportByUUID(Object id){
        return hibernator.get(Report.class, Keys.UUID, id);
    }

    public void save(Report report) {
        System.out.println("Save report " + report.getUuid());
        hibernator.save(report);
        updateUtil.update(SubscribeType.reports, report.toJson());
    }

    public List<Report> getReports() {
        return hibernator.query(Report.class, null);
    }

    public List<ReportField> getFields(Object report){
        return hibernator.query(ReportField.class, REPORT, report);
    }

    public Report getReport(Object id) {
        return hibernator.get(Report.class, Keys.ID, id);
    }

    public void save(ReportField reportField) {
        hibernator.save(reportField);
    }

    public void remove(ReportField reportField) {
        hibernator.remove(reportField);
    }

    public void save(Expense expense) {
        hibernator.save(expense);
    }
}
