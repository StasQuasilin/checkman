package stanislav.vasilina.speditionclient.utils;

import android.content.Context;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import stanislav.vasilina.speditionclient.entity.Report;
import stanislav.vasilina.speditionclient.entity.ReportField;

import static android.content.Context.MODE_PRIVATE;
import static stanislav.vasilina.speditionclient.constants.Keys.ARRIVE;
import static stanislav.vasilina.speditionclient.constants.Keys.FIELDS;
import static stanislav.vasilina.speditionclient.constants.Keys.ID;
import static stanislav.vasilina.speditionclient.constants.Keys.LEAVE;
import static stanislav.vasilina.speditionclient.constants.Keys.UUID;

public class StorageUtil {

    private final String reportsDir = "report_";
    private final Context context;
    private final JSONParser parser = new JSONParser();
    private final ReportFilter reportFilter;

    public StorageUtil(Context context) {
        this.context = context;
        reportFilter = new ReportFilter(reportsDir);
    }

    public void saveReport(Report report){
        final JSONObject json = report.toJson();
        saveDate(reportsDir + report.getUuid(), json.toJSONString());
    }

    private void saveDate(String fileName, String data){
        try {
            FileOutputStream outputStream = context.openFileOutput(fileName, MODE_PRIVATE);
            outputStream.write(data.getBytes());
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File[] getFiles(){
        final File[] files = context.getFilesDir().listFiles(reportFilter);
        if (files != null){
            for (File file : files){
                System.out.println(file.getName());
            }
        }
        return files;
    }

    public List<Report> readStorage(){
        List<Report> reports = new ArrayList<>();
        final File[] files = getFiles();
        if (files != null) {
            for (File file : files) {
                final Report report = readFile(file.getName());
                if (report != null){
                    reports.add(report);
                }
            }
        }
        return reports;
    }

    private Report readFile(String name) {
        try {
            final FileInputStream fileInputStream = context.openFileInput(name);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream));
            StringBuilder builder = new StringBuilder();
            String s;
            while ((s = reader.readLine()) != null){
                builder.append(s);
            }

            final JSONObject parse = (JSONObject) parser.parse(builder.toString());
            Report report = new Report();
            report.setId(Integer.parseInt(String.valueOf(parse.get(ID))));
            report.setUuid(String.valueOf(parse.get(UUID)));
            if (parse.containsKey(LEAVE)) {
                report.setLeaveTime(Timestamp.valueOf(String.valueOf(parse.get(LEAVE))));
            }
            if (parse.containsKey(FIELDS)) {
                final Object fields = parse.get(FIELDS);
                if (fields != null) {
                    for (Object o : (JSONArray)fields) {
                        JSONObject field = (JSONObject) o;

                        long arrive = Long.parseLong(String.valueOf(field.get(ARRIVE)));
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTimeInMillis(arrive);
                        ReportField reportField = new ReportField(calendar);
                        report.addField(reportField);
                    }
                }
            }

            return report;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void clearStorage(){
        final File[] files = context.getFilesDir().listFiles();
        if (files != null){
            for (File file : files){
                file.delete();
            }
        }
    }

    public Report openReport(String uuid) {
        return readFile(reportsDir + uuid);
    }
}
