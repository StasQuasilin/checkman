package ua.svasilina.spedition.utils.sync;

import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ua.svasilina.spedition.constants.ApiLinks;
import ua.svasilina.spedition.entity.Report;
import ua.svasilina.spedition.entity.sync.SyncList;
import ua.svasilina.spedition.entity.sync.SyncListItem;
import ua.svasilina.spedition.utils.JsonParser;
import ua.svasilina.spedition.utils.LoginUtil;
import ua.svasilina.spedition.utils.NetworkUtil;
import ua.svasilina.spedition.utils.ReportsUtil;
import ua.svasilina.spedition.utils.network.Connector;

import static ua.svasilina.spedition.constants.Keys.STATUS;
import static ua.svasilina.spedition.constants.Keys.SUCCESS;
import static ua.svasilina.spedition.constants.Keys.TOKEN;

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
        if (report != null) {
            final String token = loginUtil.getToken();
            if (token != null) {
                final String uuid = report.getUuid();
                if (!nowSync.contains(uuid)) {
                    nowSync.add(uuid);

                    final JSONObject object = new JSONObject(report.toJson());

                    JsonObjectRequest request = new JsonObjectRequest(
                            Request.Method.POST,
                            ApiLinks.REPORT_SAVE,
                            object,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    System.out.println(response);
                                    try {
                                        final String status = response.getString(STATUS);
                                        if (status.equals(SUCCESS)){
                                            syncList.setSyncTime(uuid);
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(reportsUtil.getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                            }
                    }){
                        @Override
                        public Map<String, String> getHeaders() {
                            final HashMap<String, String> map = new HashMap<>();
                            map.put(TOKEN, token);
                            map.put("content-type", "application/json; charset=utf-8");
                            return map;
                        }
                    };

                    Connector.getConnector().addRequest(reportsUtil.getContext(), request);
                }
            }
        }
    }

    private void sync(final String uuid){
        final Report openReport = reportsUtil.openReport(uuid);
        sync(openReport);
    }
}