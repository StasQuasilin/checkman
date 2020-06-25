package api.reports;

import api.ServletAPI;
import constants.ApiLinks;
import constants.Keys;
import entity.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.hibernate.dao.DriverDAO;
import utils.hibernate.dao.ProductDAO;
import utils.hibernate.dao.ReportDAO;
import utils.hibernate.dao.UserDAO;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static constants.Keys.*;

@WebServlet(ApiLinks.REPORT_SAVE)
public class SaveReportAPI extends ServletAPI {

    private final UserDAO userDAO = new UserDAO();
    private final ReportDAO reportDAO = new ReportDAO();
    private final ProductDAO productDAO = new ProductDAO();
    private final DriverDAO driverDAO = new DriverDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        JSONObject body = parseBody(req);
        ServerAnswer answer;
        if (body != null) {
            System.out.println("!" + body);
            final String header = req.getHeader(TOKEN);
            final User user = userDAO.getUserByToken(header);
            Report report = reportDAO.getReportByUUID(body.get(Keys.ID));

            if (report == null){
                report = new Report();
                report.setOwner(user);
            }

            if (body.containsKey(LEAVE)) {
                Timestamp leave = new Timestamp(Long.parseLong(String.valueOf(body.get(LEAVE))));
                report.setLeaveTime(leave);
            }

            if (body.containsKey(DONE)){
                Timestamp done = new Timestamp(Long.parseLong(String.valueOf(body.get(DONE))));
                report.setDone(done);
            }

            final Product product = productDAO.getProduct(body.get(PRODUCT));
            report.setProduct(product);

            if(body.containsKey(WEIGHT)){
                final Object o = body.get(WEIGHT);
                if (o != null){
                    Weight weight = report.getWeight();
                    if (weight == null){
                        weight = new Weight();
                        report.setWeight(weight);
                    }
                    parseWeight(weight, (JSONObject)o);
                }
            }

            if (body.containsKey(ROUTE)) {
                JSONObject route = (JSONObject) body.get(ROUTE);
                if (route.containsKey(ROUTE)){
                    JSONArray routeArray = (JSONArray) route.get(ROUTE);
                    StringBuilder stringBuilder = new StringBuilder();
                    int i = 0;
                    for (Object o : routeArray){
                        stringBuilder.append(o);
                        if (i < routeArray.size() - 1){
                            stringBuilder.append(COMA);
                        }
                        i++;
                    }
                    report.setRoute(stringBuilder.toString());
                }
            }

            report.setUuid(String.valueOf(body.get(Keys.ID)));

            if(body.containsKey(DRIVER)){
                JSONObject driverJson = (JSONObject) body.get(DRIVER);
                Driver driver = driverDAO.getDriverByUUID(driverJson.get(ID));
                if (driver == null){
                    driver = new Driver();
                    driver.setPerson(new Person());
                }
                driver.setUuid(String.valueOf(driverJson.get(ID)));
                final Person person = driver.getPerson();

                person.setSurname(String.valueOf(driverJson.get(SURNAME)));
                person.setForename(String.valueOf(driverJson.get(FORENAME)));

                driverDAO.save(driver);

                final HashMap<String, Phone> phones = new HashMap<>();
                if (person.getPhones() != null) {
                    for (Phone phone : person.getPhones()) {
                        phones.put(phone.getNumber(), phone);
                    }
                }
                final Object o = driverJson.get(PHONES);
                if (o != null){
                    for (Object p : (JSONArray)o){
                        String number = String.valueOf(p);
                        Phone phone = phones.remove(number);
                        if (phone == null){
                            phone = new Phone();
                            phone.setPerson(person);
                            phone.setNumber(number);
                            userDAO.save(phone);
                        }
                    }
                }
                for (Map.Entry<String, Phone> entry : phones.entrySet()){
                    userDAO.remove(entry.getValue());
                }

                report.setDriver(driver);
            } else {
                report.setDriver(null);
            }

            int perDiem = Integer.parseInt(String.valueOf(body.get(PER_DIEM)));
            report.setPerDiem(perDiem);

            reportDAO.save(report);
            saveFields(report, (JSONArray) body.get(FIELDS));
            saveExpenses(report, report.getFares(), (JSONArray) body.get(FARES), ExpenseType.fare);
            saveExpenses(report, report.getExpenses(), (JSONArray) body.get(EXPENSES), ExpenseType.expense);
            saveNotes(report, (JSONArray) body.get(NOTES));

            answer = new SuccessAnswer();
            write(resp, answer.toJson());
            reportDAO.afterSave(report);
        }
    }

    private void parseWeight(Weight weight, JSONObject o) {
        float gross = Float.parseFloat(String.valueOf(o.get(GROSS)));
        float tare = Float.parseFloat(String.valueOf(o.get(TARE)));
        weight.setGross(gross);
        weight.setTare(tare);
        reportDAO.save(weight);
    }

    private void saveNotes(Report report, JSONArray array) {
        final HashMap<String, ReportNote> noteHashMap = new HashMap<>();
        for (ReportNote note : reportDAO.getNotes(report)){
            noteHashMap.put(note.getUuid(), note);
        }

        for (Object o : array){
            JSONObject json = (JSONObject) o;
            String uuid = String.valueOf(json.get(ID));
            ReportNote reportNote = noteHashMap.remove(uuid);
            if (reportNote == null){
                reportNote = new ReportNote();
                reportNote.setReport(report);
                reportNote.setUuid(uuid);
            }

            Timestamp time = new Timestamp(Long.parseLong(String.valueOf(json.get(TIME))));
            reportNote.setTime(time);

            String note = String.valueOf(json.get(NOTE));
            reportNote.setNote(note);

            reportDAO.save(reportNote);
        }
        for (Map.Entry<String, ReportNote> entry : noteHashMap.entrySet()){
            reportDAO.remove(entry.getValue());
        }
    }

    private void saveExpenses(Report report, Set<Expense> expenses, JSONArray expensesList, ExpenseType type) {
        HashMap<String, Expense> expenseHashMap = new HashMap<>();
        for (Expense expense : expenses){
            expenseHashMap.put(expense.getUuid(), expense);
        }
        for (Object o : expensesList){
            JSONObject json = (JSONObject) o;
            String uuid = String.valueOf(json.get(ID));
            Expense expense = expenseHashMap.remove(uuid);
            if (expense == null){
                expense = new Expense();
                expense.setReport(report);
                expense.setUuid(uuid);
                expense.setType(type);
            }

            String description = String.valueOf(json.get(DESCRIPTION));
            expense.setDescription(description);

            int amount = Integer.parseInt(String.valueOf(json.get(AMOUNT)));
            expense.setAmount(amount);

            reportDAO.save(expense);
        }
    }

    private void saveFields(Report report, JSONArray fields) {
        HashMap<String, ReportField> fieldHashMap = new HashMap<>();
        for (ReportField rf : reportDAO.getFields(report)){
            fieldHashMap.put(rf.getUuid(), rf);
        }
        for (Object o : fields){
            JSONObject field = (JSONObject) o;
            String id = String.valueOf(field.get(ID));
            ReportField reportField = fieldHashMap.remove(id);
            if (reportField == null){
                reportField = new ReportField();
                reportField.setReport(report);
                reportField.setUuid(id);
            }

            if (field.containsKey(ARRIVE)){
                Timestamp arrive = new Timestamp(Long.parseLong(String.valueOf(field.get(ARRIVE))));
                reportField.setArriveTime(arrive);
            }

            if (field.containsKey(LEAVE)){
                Timestamp leave = new Timestamp(Long.parseLong(String.valueOf(field.get(LEAVE))));
                reportField.setLeaveTime(leave);
            }

            if (field.containsKey(COUNTERPARTY)){
                String counterparty = String.valueOf(field.get(COUNTERPARTY)).toUpperCase();
                reportField.setCounterparty(counterparty);
            }

            int money = Integer.parseInt(String.valueOf(field.get(MONEY)));
            reportField.setMoney(money);

            if (field.containsKey(WEIGHT)){
                final Object w = field.get(WEIGHT);
                if (w != null) {
                    Weight weight = reportField.getWeight();
                    if (weight == null) {
                        weight = new Weight();
                        reportField.setWeight(weight);
                    }
                    parseWeight(weight, (JSONObject) w);
                }
            }

            reportDAO.save(reportField);
        }
        for (Map.Entry<String, ReportField> entry : fieldHashMap.entrySet()){
            reportDAO.remove(entry.getValue());
        }
    }
}

