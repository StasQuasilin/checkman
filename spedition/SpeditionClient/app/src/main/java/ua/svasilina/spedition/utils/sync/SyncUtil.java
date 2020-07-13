package ua.svasilina.spedition.utils.sync;

import android.util.Log;

import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import ua.svasilina.spedition.constants.ApiLinks;
import ua.svasilina.spedition.entity.Report;
import ua.svasilina.spedition.entity.sync.SyncList;
import ua.svasilina.spedition.entity.sync.SyncListItem;
import ua.svasilina.spedition.utils.JsonParser;
import ua.svasilina.spedition.utils.LoginUtil;
import ua.svasilina.spedition.utils.NetworkUtil;
import ua.svasilina.spedition.utils.ReportsUtil;

import static ua.svasilina.spedition.constants.Keys.STATUS;
import static ua.svasilina.spedition.constants.Keys.SUCCESS;

public class SyncUtil {

    private final ReportsUtil reportsUtil;
    private final NetworkUtil networkUtil;
    private final LoginUtil loginUtil;
    private final JsonParser parser;
    private final SyncListUtil syncList;

    public SyncUtil(ReportsUtil reportsUtil) {
        this.reportsUtil = reportsUtil;
        networkUtil = new NetworkUtil();
        loginUtil  = new LoginUtil(reportsUtil.getContext());
        parser = new JsonParser();
        syncList = new SyncListUtil(reportsUtil.getContext());
    }

    public void sync(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                final SyncList list = syncList.readSyncList();
                for (SyncListItem item : list.getFields()){
                    if (item.getSyncTime() == null){
                        sync(item.getReport());
                    }
                }
            }
        }).start();
    }
    private final Set<String> nowSync = new HashSet<>();

    private void sync(final Report report){
        final String token = loginUtil.getToken();
        if (token != null) {
            final String uuid = report.getUuid();
            if (!nowSync.contains(uuid)) {
                nowSync.add(uuid);

                final JSONObject jsonObject = report.toJson();
                try {
                    final String post = networkUtil.post(ApiLinks.REPORT_SAVE, jsonObject.toJSONString(), token);
                    Log.i("Result", post);
                    final JSONObject answer = parser.parse(post);
                    if (answer != null) {
                        String status = String.valueOf(answer.get(STATUS));
                        if (status.equals(SUCCESS)) {
                            syncList.setSyncTime(uuid);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                nowSync.remove(uuid);
            }
        }
    }

    private void sync(final String uuid){
        final Report openReport = reportsUtil.openReport(uuid);
        sync(openReport);
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
