package api.weight;
import api.ServletAPI;

import bot.TelegramBotFactory;
import bot.TelegramNotificator;
import constants.Branches;
import entity.Subdivision;
import entity.transport.ActionTime;
import entity.weight.RoundReport;
import entity.weight.SubdivisionReport;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.UpdateUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashSet;

@WebServlet(Branches.API.REPORT_EDIT)
public class ReportEditAPI extends ServletAPI {

    private final UpdateUtil updateUtil = new UpdateUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if(body != null){
            RoundReport report = dao.getObjectById(RoundReport.class, body.get(ID));
            boolean isNew = false;
            boolean save = false;
            if (report == null){
                isNew = true;
                report = new RoundReport();
                ActionTime actionTime = new ActionTime(getWorker(req));
                report.setCreateTime(actionTime);
            }

            Date date = Date.valueOf(String.valueOf(body.get(DATE)));
            Time time = Time.valueOf(String.valueOf(body.get(TIME)));
            Timestamp timestamp = Timestamp.valueOf(
                    LocalDateTime.of(
                            date.toLocalDate(),
                            time.toLocalTime()
                    )
            );

            if (report.getTimestamp() == null || !report.getTimestamp().equals(timestamp)){
                report.setTimestamp(timestamp);
                save = true;
            }

            HashMap<Integer, SubdivisionReport> reports = new HashMap<>();
            if (report.getReports() != null) {
                for (SubdivisionReport r : report.getReports()) {
                    reports.put(r.getId(), r);
                }
                report.getReports().clear();
            } else {
                report.setReports(new LinkedHashSet<>());
            }

            for (Object o : (JSONArray)body.get(REPORTS)){
                JSONObject json = (JSONObject) o;
                int id = Integer.parseInt(String.valueOf(json.get(ID)));
                SubdivisionReport r;
                if(reports.containsKey(id)){
                    r = reports.remove(id);
                } else {
                    r = new SubdivisionReport();
                    r.setReport(report);
                    r.setSubdivision(dao.getObjectById(Subdivision.class, json.get(SUBDIVISION)));
                }
                boolean serviceability = Boolean.parseBoolean(String.valueOf(json.get(SERVICEABILITY)));
                r.setServiceability(serviceability);
                boolean ah = Boolean.parseBoolean(String.valueOf(json.get(ADHERENCE)));
                r.setAdherence(ah);

                String note = String.valueOf(json.get(NOTE));
                r.setNote(note);
                report.getReports().add(r);
            }

            if (save){
                if(isNew){
                    dao.save(report.getCreateTime());
                }
                dao.save(report);
                for (SubdivisionReport r : report.getReports()){
                    dao.save(r);
                }

                TelegramNotificator notificator = TelegramBotFactory.getTelegramNotificator();
                if(notificator != null){
                    notificator.sendReport(report);
                }
                updateUtil.onSave(report);
            }
        }
        write(resp, SUCCESS_ANSWER);
    }
}
