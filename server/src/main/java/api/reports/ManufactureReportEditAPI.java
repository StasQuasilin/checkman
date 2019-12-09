package api.reports;

import api.ServletAPI;
import bot.BotFactory;
import bot.Notificator;
import constants.Branches;
import entity.manufactoring.ReportMessageLink;
import entity.production.Turn;
import entity.reports.ManufactureReport;
import entity.reports.ReportField;
import entity.reports.ReportFieldCategory;
import entity.reports.ReportFieldSettings;
import entity.storages.Storage;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.telegram.telegrambots.meta.api.objects.Message;
import utils.TurnDateTime;
import utils.U;
import utils.UpdateUtil;
import utils.answers.SuccessAnswer;
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
import java.util.HashMap;

/**
 * Created by szpt_user045 on 17.09.2019.
 */
@WebServlet(Branches.API.SAVE_MANUFACTURE_REPORT)
public class ManufactureReportEditAPI extends ServletAPI {

    final Logger log = Logger.getLogger(ManufactureReportEditAPI.class);
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
                JSONObject field = (JSONObject) o;
                log.info(field.get(TITLE) + COLON + SPACE + field.get(VALUE));
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
                    if (field.containsKey("index")){
                        int index = Integer.parseInt(String.valueOf(field.get("index")));
                        reportField.setIndex(index);
                    }
                }

                String title = String.valueOf(field.get("title"));
                if (reportField.getTitle() == null || !reportField.getTitle().equals(title)){
                    reportField.setTitle(title);
                    save = true;
                }
                String valueString = String.valueOf(field.get("value"));
                float value = 0;
                if (U.exist(valueString)){
                    value = Float.parseFloat(valueString);
                }

                if (reportField.getValue() != value){
                    reportField.setValue(value);
                    save = true;
                }



                long unitId = (long) field.get("unit");
                if (reportField.getUnit() == null || reportField.getUnit().getId() != unitId) {
                    reportField.setUnit(dao.getWeightUnitById(unitId));
                    save=true;
                }

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

                    reportField.setCategory(category);
                    if (!field.containsKey("setting")) {
                        if (!Boolean.parseBoolean(String.valueOf(field.get("once")))){
                            ReportFieldSettings settings = new ReportFieldSettings();
                            settings.setTitle(String.valueOf(field.get("title")));
                            settings.setUnit(dao.getWeightUnitById(field.get("unit")));
                            settings.setCategory(category);
                            dao.save(settings);
                        }
                    }
                }
                String comment = String.valueOf(field.get("comment"));
                if (!U.exist(reportField.getComment()) || !reportField.getComment().equals(comment) ){
                    reportField.setComment(comment);
                    save = true;
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
                write(resp, parser.toJson(new SuccessAnswer(ID, manufactureReport.getId())).toJSONString());

//                Notificator notificator = BotFactory.getNotificator();
//                if (notificator != null){

//                    ArrayList<Message> messages = new ArrayList<>();
//                    notificator.manufactureReportShow(manufactureReport, messages);
//

//                    }
//                }
            } else {
                write(resp, SUCCESS_ANSWER);
            }
        }
    }
}
