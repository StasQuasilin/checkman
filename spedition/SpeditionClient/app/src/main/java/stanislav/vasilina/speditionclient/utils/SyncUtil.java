package stanislav.vasilina.speditionclient.utils;

import android.util.Log;

import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.List;

import stanislav.vasilina.speditionclient.constants.ApiLinks;
import stanislav.vasilina.speditionclient.entity.Report;

import static stanislav.vasilina.speditionclient.constants.Keys.STATUS;
import static stanislav.vasilina.speditionclient.constants.Keys.SUCCESS;

public class SyncUtil {

    private final ReportsUtil reportsUtil;
    private final NetworkUtil networkUtil;
    private final LoginUtil loginUtil;
    private final JsonParser parser;

    public SyncUtil(ReportsUtil reportsUtil) {
        this.reportsUtil = reportsUtil;
        networkUtil = new NetworkUtil();
        loginUtil  = new LoginUtil(reportsUtil.getContext());
        parser = new JsonParser();
    }

    public void sync(final List<Report> reportList){
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (Report report : reportList){
                    if (!report.isSync()){
                        final Report openReport = reportsUtil.openReport(report.getUuid());
                        sync(openReport);
                    }
                }
            }
        }).start();
        new Thread(){
            @Override
            public void run() {

            }
        }.start();
    }

    private void sync(final Report report){
        report.setSync(false);
        final String token = loginUtil.getToken();
        if (token != null) {
            final JSONObject jsonObject = report.toJson();
            try {
                final String post = networkUtil.post(ApiLinks.REPORT_SAVE, jsonObject.toJSONString(), token);
                Log.i("Result", post);
                final JSONObject answer = parser.parse(post);
                if (answer != null){
                    String status = String.valueOf(answer.get(STATUS));
                    if (status.equals(SUCCESS)){
                        report.setSync(true);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        reportsUtil.saveReport(report);
    }

    public void syncThread(final Report report){
        new Thread(new Runnable() {
            @Override
            public void run() {
                sync(report);
            }
        }).start();
    }
}
