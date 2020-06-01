package stanislav.vasilina.speditionclient.utils;

import org.json.simple.JSONObject;

import java.io.IOException;

import stanislav.vasilina.speditionclient.constants.ApiLinks;
import stanislav.vasilina.speditionclient.entity.Report;

import static stanislav.vasilina.speditionclient.constants.Keys.SUCCESS;

public class SyncUtil {

    private final ReportsUtil reportsUtil;
    private final NetworkUtil networkUtil = new NetworkUtil();

    public SyncUtil(ReportsUtil reportsUtil) {
        this.reportsUtil = reportsUtil;
    }

    public void sync(){
        new Thread(){
            @Override
            public void run() {
                for (Report report : reportsUtil.readStorage(ReportDetail.no)){
                    if (!report.isSync()){
                        final Report openReport = reportsUtil.openReport(report.getUuid());
                        sync(openReport);
                    }
                }
            }
        }.start();
    }

    private void sync(final Report report){
        final JSONObject jsonObject = report.toJson();
        try {
            final String post = networkUtil.post(ApiLinks.REPORT_SAVE, jsonObject.toJSONString());
            if (post.equals(SUCCESS)) {
                report.setSync(true);
                report.setSync(true);
                reportsUtil.saveReport(report);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void syncThread(final Report report){
        new Thread(){
            @Override
            public void run() {
                sync(report);
            }
        }.start();
    }
}
