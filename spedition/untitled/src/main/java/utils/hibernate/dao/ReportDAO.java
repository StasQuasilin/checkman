package utils.hibernate.dao;

import api.socket.SubscribeType;
import api.socket.UpdateUtil;
import constants.Keys;
import entity.*;
import utils.hibernate.Hibernator;
import utils.hibernate.State;

import java.util.HashMap;
import java.util.List;

import static constants.Keys.REPORT;

public class ReportDAO {
    private final Hibernator hibernator = Hibernator.getInstance();
    private final UpdateUtil updateUtil = new UpdateUtil();

    public Report getReportByUUID(Object id){
        return hibernator.get(Report.class, Keys.UUID, id);
    }

    public void save(Report report) {
        hibernator.save(report);
        final User owner = report.getOwner();
        updateUtil.update(SubscribeType.reports, report.toJson(), owner);
        if (owner.getSupervisor() != null){
            updateUtil.update(SubscribeType.reports, report.toJson(), owner.getSupervisor());
        }
    }

    public List<Report> getReports(User user) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("leaveTime", State.notNull);
        final Role role = user.getRole();
        if (role == Role.supervisor){
            params.put("owner/supervisor", user);
        } else {
            params.put("owner", user);
        }

        return hibernator.query(Report.class, params);
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

    public void remove(Object item) {
        hibernator.remove(item);
    }

    public void save(Expense expense) {
        hibernator.save(expense);
    }

    public List<ReportNote> getNotes(Object report) {
        return hibernator.query(ReportNote.class, REPORT, report);
    }

    public void save(ReportNote reportNote) {
        hibernator.save(reportNote);
    }

    public void save(Weight weight) {
        hibernator.save(weight);
    }
}
