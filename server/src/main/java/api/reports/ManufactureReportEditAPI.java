package api.reports;

import api.ServletAPI;
import constants.Branches;
import entity.production.Turn;
import entity.reports.ManufactureReport;
import entity.reports.ReportField;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.TurnDateTime;
import utils.turns.TurnBox;
import utils.turns.TurnService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 * Created by szpt_user045 on 17.09.2019.
 */
@WebServlet(Branches.API.SAVE_MANUFACTURE_REPORT)
public class ManufactureReportEditAPI extends ServletAPI {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        System.out.println(body);
        if (body != null) {
            JSONObject report = (JSONObject) body.get("report");
            JSONArray fields = (JSONArray) body.get("fields");
            long reportId = -1;
            if (report.containsKey("id")){
                reportId = (long) report.get("id");
            }
            ManufactureReport manufactureReport;
            if (reportId != -1){
                manufactureReport = dao.getManufactureReport(reportId);
            } else {
                manufactureReport = new ManufactureReport();
            }
            boolean save = false;
            Date date = Date.valueOf(String.valueOf(report.get("date")));
            int turnNumber = Integer.parseInt(String.valueOf(report.get("turn")));
            LocalDate localDate = date.toLocalDate();
            LocalTime localTime = TurnBox.getTurnByNumber(turnNumber).getBegin().toLocalTime();
            LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
            TurnDateTime turnDate = TurnBox.getTurnDate(localDateTime);

            Turn target = TurnService.getTurn(turnDate);
            Turn current = manufactureReport.getTurn();
            if (current == null || current.getId() != target.getId()){
                manufactureReport.setTurn(target);
                save = true;
            }
            ArrayList<ReportField> reportFields = new ArrayList<>();

            for(Object field : fields){
                System.out.println(field);
                JSONObject fj = (JSONObject) field;
                long fieldId = -1;
                if (fj.containsKey("id")){
                    fieldId = (long) fj.get("id");
                }
                ReportField reportField;
                if (fieldId != -1){
                    reportField = dao.getReportField(fieldId);
                } else {
                    reportField = new ReportField();
                    reportField.setReport(manufactureReport);
                }

                String title = String.valueOf(fj.get("title"));
                if (reportField.getTitle() == null || !reportField.getTitle().equals(title)){
                    reportField.setTitle(title);
                    save = true;
                }

                float value = Float.parseFloat(String.valueOf(fj.get("value")));
                if (reportField.getValue() != value){
                    reportField.setValue(value);
                    save = true;
                }

                long unitId = (long) fj.get("unit");
                if (reportField.getUnit() == null || reportField.getUnit().getId() != unitId) {
                    reportField.setUnit(dao.getWeightUnitById(unitId));
                    save=true;
                }

                reportFields.add(reportField);
            }

            if (save){
                manufactureReport.setCreator(getWorker(req));
                dao.save(manufactureReport);
                reportFields.forEach(dao::save);
            }

            write(resp, SUCCESS_ANSWER);
        }
    }
}
