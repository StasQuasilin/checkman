package stanislav.vasilina.speditionclient.utils;

import android.content.Context;
import android.util.Log;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import stanislav.vasilina.speditionclient.constants.ApiLinks;
import stanislav.vasilina.speditionclient.entity.Driver;
import stanislav.vasilina.speditionclient.entity.Person;
import stanislav.vasilina.speditionclient.entity.Report;
import stanislav.vasilina.speditionclient.entity.ReportField;

import static stanislav.vasilina.speditionclient.constants.Keys.ANSWER;
import static stanislav.vasilina.speditionclient.constants.Keys.ARRIVE;
import static stanislav.vasilina.speditionclient.constants.Keys.DRIVER;
import static stanislav.vasilina.speditionclient.constants.Keys.FIELDS;
import static stanislav.vasilina.speditionclient.constants.Keys.FORENAME;
import static stanislav.vasilina.speditionclient.constants.Keys.ID;
import static stanislav.vasilina.speditionclient.constants.Keys.LEAVE;
import static stanislav.vasilina.speditionclient.constants.Keys.PERSON;
import static stanislav.vasilina.speditionclient.constants.Keys.SUCCESS;
import static stanislav.vasilina.speditionclient.constants.Keys.SURNAME;
import static stanislav.vasilina.speditionclient.constants.Keys.SYNC;
import static stanislav.vasilina.speditionclient.constants.Keys.UID;

public class ReportsUtil {
    private static final String TAG = "ReportsUtil";
    private final JSONParser parser = new JSONParser();
    private StorageUtil storageUtil;
    private NetworkUtil networkUtil;

    private static final String reportsDir = "report_";
    private final FileFilter fileFilter;
    private final Context context;
    public ReportsUtil(Context context) {
        storageUtil = new StorageUtil(context);
        networkUtil = new NetworkUtil();
        fileFilter = new FileFilter(reportsDir);
        this.context = context;
    }

    public void saveReport(final Report report){

        String uuid = report.getUuid();
        if(uuid == null){
            uuid = UUID.randomUUID().toString();
            report.setUuid(uuid);
        }
        final String fileName = reportsDir + uuid;
        final JSONObject jsonObject = report.toJson();
        storageUtil.saveDate(fileName, jsonObject.toJSONString());
        new Thread(){
            public void run(){
            try {
                final String post = networkUtil.post(ApiLinks.REPORT_SAVE, jsonObject.toJSONString());
                if (post != null && !post.isEmpty()) {
                    try {
                        final JSONObject parse = (JSONObject) parser.parse(post);
                        if (parse.containsKey(ANSWER)) {
                            final String ans = String.valueOf(parse.get(ANSWER));
                            if (ans.equals(SUCCESS)) {
                                report.setSync(true);
                                jsonObject.put(SYNC, true);
                            }
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                storageUtil.saveDate(fileName, jsonObject.toJSONString());

            } catch (IOException e) {
                e.printStackTrace();
            }
            }
        }.start();
    }

    private Report parseReport(String data, boolean detailed){

        Report report = null;
        try {
            System.out.println(data);
            final JSONObject parse = (JSONObject) parser.parse(data);
            report = new Report();
            report.setId(Integer.parseInt(String.valueOf(parse.get(ID))));
            report.setUuid(String.valueOf(parse.get(UID)));
            if(parse.containsKey(DRIVER)){
                Driver driver = new Driver();
                final JSONObject driverJson = (JSONObject) parse.get(DRIVER);
                driver.setId(Integer.parseInt(String.valueOf(driverJson.get(ID))));
                Person person = new Person();
                final JSONObject personJson = (JSONObject) driverJson.get(PERSON);
                person.setId(Integer.parseInt(String.valueOf(personJson.get(ID))));
                person.setSurname(String.valueOf(personJson.get(SURNAME)));
                person.setForename(String.valueOf(personJson.get(FORENAME)));
                driver.setPerson(person);
                report.setDriver(driver);
            }
            if (parse.containsKey(LEAVE)) {
                System.out.println(parse.get(LEAVE));
                final Calendar instance = Calendar.getInstance();
                instance.setTimeInMillis(Long.parseLong(String.valueOf(parse.get(LEAVE))));
                report.setLeaveTime(instance);
            }
            if (detailed) {
                if (parse.containsKey(FIELDS)) {
                    final Object fields = parse.get(FIELDS);
                    if (fields != null) {
                        for (Object o : (JSONArray) fields) {
                            JSONObject field = (JSONObject) o;

                            long arrive = Long.parseLong(String.valueOf(field.get(ARRIVE)));
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTimeInMillis(arrive);
                            ReportField reportField = new ReportField(calendar);
                            report.addField(reportField);
                        }
                    }
                }
            }
            if (parse.containsKey(SYNC)){
                report.setSync(Boolean.parseBoolean(String.valueOf(parse.get(SYNC))));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return report;
    }

    public List<Report> readStorage(){
        List<Report> reports = new ArrayList<>();
        final File[] files = storageUtil.getFiles(fileFilter);
        if (files != null) {
            for (File file : files) {
                final String s = storageUtil.readFile(file.getName());
                Report report = parseReport(s, false);
                if (report != null){
                    reports.add(report);
                }
            }
        }
        return reports;
    }

    public Report openReport(String uuid) {
        Log.i(TAG, "Open report " + uuid);
        final String s = storageUtil.readFile(reportsDir + uuid);
        return parseReport(s, true);
    }

    public void clearStorage() {
        storageUtil.clearStorage(fileFilter);
    }
}
