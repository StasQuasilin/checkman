package ua.svasilina.spedition.utils;

import android.content.Context;
import android.util.Log;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import ua.svasilina.spedition.entity.Driver;
import ua.svasilina.spedition.entity.Expense;
import ua.svasilina.spedition.entity.Report;
import ua.svasilina.spedition.entity.ReportField;
import ua.svasilina.spedition.entity.ReportNote;
import ua.svasilina.spedition.entity.Route;
import ua.svasilina.spedition.entity.Weight;

import static ua.svasilina.spedition.constants.Keys.AMOUNT;
import static ua.svasilina.spedition.constants.Keys.ARRIVE;
import static ua.svasilina.spedition.constants.Keys.COUNTERPARTY;
import static ua.svasilina.spedition.constants.Keys.DESCRIPTION;
import static ua.svasilina.spedition.constants.Keys.DONE;
import static ua.svasilina.spedition.constants.Keys.DRIVER;
import static ua.svasilina.spedition.constants.Keys.EXPENSES;
import static ua.svasilina.spedition.constants.Keys.FARE;
import static ua.svasilina.spedition.constants.Keys.FIELDS;
import static ua.svasilina.spedition.constants.Keys.FONE;
import static ua.svasilina.spedition.constants.Keys.GROSS;
import static ua.svasilina.spedition.constants.Keys.ID;
import static ua.svasilina.spedition.constants.Keys.LEAVE;
import static ua.svasilina.spedition.constants.Keys.MONEY;
import static ua.svasilina.spedition.constants.Keys.NOTE;
import static ua.svasilina.spedition.constants.Keys.NOTES;
import static ua.svasilina.spedition.constants.Keys.PER_DIEM;
import static ua.svasilina.spedition.constants.Keys.PRODUCT;
import static ua.svasilina.spedition.constants.Keys.ROUTE;
import static ua.svasilina.spedition.constants.Keys.SYNC;
import static ua.svasilina.spedition.constants.Keys.TARE;
import static ua.svasilina.spedition.constants.Keys.TIME;
import static ua.svasilina.spedition.constants.Keys.WEIGHT;

public class ReportsUtil {
    private static final String TAG = "ReportsUtil";
    private final JSONParser parser = new JSONParser();
    private StorageUtil storageUtil;
    private ProductsUtil productsUtil = new ProductsUtil();
    private static final int STORAGE_SIZE = 20;

    private static final String reportsDir = "report_";
    private final FileFilter fileFilter;
    private final Context context;
    private final SyncUtil syncUtil;
    public ReportsUtil(Context context) {
        storageUtil = new StorageUtil(context);
        fileFilter = new FileFilter(reportsDir);
        this.context = context;
        syncUtil = new SyncUtil(this);
    }

    public Context getContext() {
        return context;
    }

    public void saveAndSync(final Report report){
        report.setSync(false);
        saveReport(report);
        syncUtil.syncThread(report);
    }

    public void saveReport(final Report report){
        String uuid = report.getUuid();
        if(uuid == null){
            uuid = UUID.randomUUID().toString();
            report.setUuid(uuid);
        }
        final String fileName = reportsDir + uuid;
        final JSONObject jsonObject = report.toJson();
        final String s = jsonObject.toJSONString();
        Log.i("Storage Util", s);
        storageUtil.saveDate(fileName, s);
    }

    private Report parseReport(String data, ReportDetail detailed){

        Report report = null;
        try {
            System.out.println(data);
            final JSONObject parse = (JSONObject) parser.parse(data);
            report = new Report();
            if (parse.containsKey(SYNC)){
                report.setSync(Boolean.parseBoolean(String.valueOf(parse.get(SYNC))));
            }

            report.setUuid(String.valueOf(parse.get(ID)));
            if (detailed == ReportDetail.no) {
                if (parse.containsKey(SYNC)) {
                    report.setSync(Boolean.parseBoolean(String.valueOf(parse.get(SYNC))));
                }
            } else if (detailed == ReportDetail.info || detailed == ReportDetail.full) {
                if (parse.containsKey(DRIVER)) {
                    final JSONObject driverJson = (JSONObject) parse.get(DRIVER);
                    report.setDriver(Driver.fromJson(driverJson));
                }
                if (parse.containsKey(LEAVE)) {
                    final Calendar instance = Calendar.getInstance();
                    instance.setTimeInMillis(Long.parseLong(String.valueOf(parse.get(LEAVE))));
                    report.setLeaveTime(instance);
                }
                if (parse.containsKey(DONE)) {
                    final Calendar instance = Calendar.getInstance();
                    instance.setTimeInMillis(Long.parseLong(String.valueOf(parse.get(DONE))));
                    report.setDoneDate(instance);
                }
                if (parse.containsKey(ROUTE)) {
                    Route route = new Route();
                    JSONObject routeJson = (JSONObject) parse.get(ROUTE);
                    if (routeJson != null) {
                        final JSONArray routeArray = (JSONArray) routeJson.get(ROUTE);
                        if (routeArray != null) {
                            for (Object o : routeArray) {
                                String point = String.valueOf(o);
                                route.addPoint(point);
                            }
                        }
                    }
                    report.setRoute(route);
                }
                if (parse.containsKey(PRODUCT)) {
                    int productId = Integer.parseInt(String.valueOf(parse.get(PRODUCT)));
                    report.setProduct(productsUtil.getProduct(productId));
                }
                if (detailed == ReportDetail.full) {

                    if (parse.containsKey(EXPENSES)){
                        final Object o = parse.get(EXPENSES);
                        if (o != null)
                            parseExpenses(report, (JSONArray)o);
                    }

                    if (parse.containsKey(FARE)) {
                        int fare = Integer.parseInt(String.valueOf(parse.get(FARE)));
                        report.setFare(fare);
                    }

                    if (parse.containsKey(PER_DIEM)) {
                        int perDiem = Integer.parseInt(String.valueOf(parse.get(PER_DIEM)));
                        report.setPerDiem(perDiem);
                    }

                    if (parse.containsKey(FONE)){
                        boolean fone = Boolean.parseBoolean(String.valueOf(parse.get(FONE)));
                        report.setFone(fone);
                    }

                    if (parse.containsKey(FIELDS)) {
                        final Object o = parse.get(FIELDS);
                        if (o != null)
                            parseFields(report, (JSONArray)o);
                    }

                    if (parse.containsKey(NOTES)){
                        final Object o = parse.get(NOTES);
                        if (o != null){
                            parseNotes(report, (JSONArray)o);
                        }
                    }
                }
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return report;
    }

    private void parseNotes(Report report, JSONArray array) {
        for (Object o : array){
            JSONObject json = (JSONObject) o;
            ReportNote note = new ReportNote();
            note.setUuid(String.valueOf(json.get(ID)));
            final Calendar instance = Calendar.getInstance();
            instance.setTimeInMillis(Long.parseLong(String.valueOf(json.get(TIME))));
            note.setTime(instance);
            note.setNote(String.valueOf(json.get(NOTE)));
            report.getNotes().add(note);
        }
    }

    private void parseFields(Report report, JSONArray fieldsArray) {
        for (Object o :  fieldsArray) {
            JSONObject field = (JSONObject) o;
            ReportField reportField = new ReportField();

            reportField.setUuid(String.valueOf(field.get(ID)));

            if (field.containsKey(ARRIVE)) {
                final long arrive = Long.parseLong(String.valueOf(field.get(ARRIVE)));
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(arrive);
                reportField.setArriveTime(calendar);
            }
            if (field.containsKey(LEAVE)){
                final long leave = Long.parseLong(String.valueOf(field.get(LEAVE)));
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(leave);
                reportField.setLeaveTime(calendar);
            }

            if (field.containsKey(COUNTERPARTY)) {
                String counterparty = String.valueOf(field.get(COUNTERPARTY));
                reportField.setCounterparty(counterparty);
            }

            if (field.containsKey(MONEY)) {
                int money = Integer.parseInt(String.valueOf(field.get(MONEY)));
                reportField.setMoney(money);
            }

            if (field.containsKey(WEIGHT)) {
                final JSONObject weightJson = (JSONObject) field.get(WEIGHT);
                if (weightJson != null) {
                    Weight weight = new Weight();
                    weight.setGross(Float.parseFloat(String.valueOf(weightJson.get(GROSS))));
                    weight.setTare(Float.parseFloat(String.valueOf(weightJson.get(TARE))));
                    reportField.setWeight(weight);
                }
            }
            report.addField(reportField);
        }
    }

    private void parseExpenses(Report report, JSONArray expensesArray) {
        for (Object o : expensesArray){
            JSONObject json = (JSONObject) o;
            Expense expense = new Expense();
            expense.setUuid(String.valueOf(json.get(ID)));
            expense.setDescription(String.valueOf(json.get(DESCRIPTION)));
            expense.setAmount(Integer.parseInt(String.valueOf(json.get(AMOUNT))));
            report.addExpense(expense);
        }
    }

    public List<Report> readStorage(){
        return readStorage(ReportDetail.info);
    }

    public List<Report> readStorage(ReportDetail detail){
        List<Report> reports = new ArrayList<>();
        final File[] files = storageUtil.getFiles(fileFilter);
        if (files != null) {
            for (File file : files) {
                final String s = storageUtil.readFile(file.getName());
                Report report = parseReport(s, detail);
                if (report != null){
                    reports.add(report);
                }
            }
        }
        Collections.sort(reports);
        while (reports.size() > STORAGE_SIZE){
            final Report report = reports.get(reports.size() - 1);
            if (report.isSync() && report.isDone()){
                reports.remove(report);
                storageUtil.remove(reportsDir + report.getUuid());
            }
        }
        syncUtil.sync(reports);

        return reports;
    }

    public Report openReport(String uuid) {
        Log.i(TAG, "Open report " + uuid);
        final String s = storageUtil.readFile(reportsDir + uuid);
        return parseReport(s, ReportDetail.full);
    }

    public void clearStorage() {
        storageUtil.clearStorage(fileFilter);
    }
}
