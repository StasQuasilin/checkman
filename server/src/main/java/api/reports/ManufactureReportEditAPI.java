package api.reports;

import api.ServletAPI;
import bot.BotFactory;
import bot.Notificator;
import constants.Branches;
import entity.production.Turn;
import entity.reports.ManufactureReport;
import entity.reports.ReportField;
import entity.reports.ReportFieldCategory;
import entity.reports.ReportFieldSettings;
import entity.storages.Storage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.TurnDateTime;
import utils.UpdateUtil;
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

    final UpdateUtil updateUtil = new UpdateUtil();

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

            for(Object o : fields){
                System.out.println(o);
                JSONObject field = (JSONObject) o;
                long fieldId = -1;
                if (field.containsKey("id")){
                    fieldId = (long) field.get("id");
                }
                ReportField reportField;
                if (fieldId != -1){
                    reportField = dao.getReportField(fieldId);
                } else {
                    reportField = new ReportField();
                    reportField.setReport(manufactureReport);
                }

                String title = String.valueOf(field.get("title"));
                if (reportField.getTitle() == null || !reportField.getTitle().equals(title)){
                    reportField.setTitle(title);
                    save = true;
                }

                float value = Float.parseFloat(String.valueOf(field.get("value")));
                if (reportField.getValue() != value){
                    reportField.setValue(value);
                    save = true;
                }

                long unitId = (long) field.get("unit");
                if (reportField.getUnit() == null || reportField.getUnit().getId() != unitId) {
                    reportField.setUnit(dao.getWeightUnitById(unitId));
                    save=true;
                }

                if (!field.containsKey("setting")) {
                    if (!Boolean.parseBoolean(String.valueOf(field.get("once")))){
                        ReportFieldSettings settings = new ReportFieldSettings();
                        if (field.containsKey("category")){
                            ReportFieldCategory category;
                            JSONObject _category = (JSONObject) field.get("category");
                            int categoryId = Integer.parseInt(String.valueOf(_category.get("id")));
                            if (categoryId == -1){
                                category = new ReportFieldCategory();
                                category.setTitle(String.valueOf(_category.get("title")));
                                dao.save(category);
                            } else {
                                category = dao.getReportCategory(categoryId);
                            }
                            settings.setCategory(category);
                        }
                        settings.setTitle(String.valueOf(field.get("title")));
                        settings.setUnit(dao.getWeightUnitById(field.get("unit")));
                        if (field.containsKey("storage")){
                            settings.setStorage(dao.getStorageById(field.get("storage")));
                        }
                        dao.save(settings);
                    }
                }

                reportFields.add(reportField);
            }

            if (save){
                manufactureReport.setCreator(getWorker(req));
                dao.save(manufactureReport);
                manufactureReport.getFields().clear();
                for (ReportField field : reportFields){
                    dao.save(field);
                    manufactureReport.getFields().add(field);
                }
                updateUtil.onSave(manufactureReport);
                Notificator notificator = BotFactory.getNotificator();
                if (notificator != null){
                    notificator.manufactureReportShow(manufactureReport);
                }
            }

            write(resp, SUCCESS_ANSWER);
        }
    }
}
