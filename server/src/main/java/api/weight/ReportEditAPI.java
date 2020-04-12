package api.weight;
import api.ServletAPI;

import bot.TelegramBot;
import bot.TelegramBotFactory;
import bot.TelegramNotificator;
import constants.Branches;
import entity.transport.ActionTime;
import entity.weight.Report;
import entity.weight.SubdivisionReport;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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

@WebServlet(Branches.API.REPORT_EDIT)
public class ReportEditAPI extends ServletAPI {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if(body != null){
            Report report = dao.getObjectById(Report.class, body.get(ID));
            boolean isNew = false;
            boolean save = false;
            if (report == null){
                isNew = true;
                report = new Report();
                ActionTime actionTime = new ActionTime(getWorker(req));
                report.setCreateTime(actionTime);
            }

            Date date = Date.valueOf(String.valueOf(body.get(DATE)));
            if (report.getDate() == null || !report.getDate().equals(date)){
                report.setDate(date);
                save = true;
            }

            Time time = Time.valueOf(String.valueOf(body.get(TIME)));
            if (report.getTime() == null || !report.getTime().equals(time)){
                report.setTime(time);
                save = true;
            }

            HashMap<Integer, SubdivisionReport> reports = new HashMap<>();
            for (SubdivisionReport r : report.getReports()){
                reports.put(r.getId(), r);
            }
            report.getReports().clear();
            for (Object o : (JSONArray)body.get(REPORTS)){
                JSONObject json = (JSONObject) o;
                int id = (int) json.get(ID);
                SubdivisionReport r;
                if(reports.containsKey(id)){
                    r = reports.remove(id);
                } else {
                    r = new SubdivisionReport();
                    r.setReport(report);
                }
                boolean good = Boolean.parseBoolean(String.valueOf(json.get(GOOD)));
                r.setGood(good);
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
            }
        }
        write(resp, SUCCESS_ANSWER);
    }
}
