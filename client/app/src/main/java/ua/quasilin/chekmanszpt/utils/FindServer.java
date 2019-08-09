package ua.quasilin.chekmanszpt.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.json.simple.JSONObject;

import ua.quasilin.chekmanszpt.activity.NoConnectionActivity;
import ua.quasilin.chekmanszpt.activity.messages.ChatsActivity;
import ua.quasilin.chekmanszpt.constants.URL;
import ua.quasilin.chekmanszpt.packets.PingPacket;
import ua.quasilin.chekmanszpt.services.HttpConnector;

/**
 * Created by szpt_user045 on 09.08.2019.
 */

public class FindServer {

    private static final String TAG = "FIND SERVER";
    private static final HttpConnector connector = HttpConnector.getConnector();

    public static boolean find(){
        boolean serverFound = checkAddress(URL.INTERNAL_SERVER_ADDRESS);
        if(!serverFound){
            serverFound = checkAddress(URL.EXTERNAL_SERVER_ADDRESS);
        }
        return serverFound;
    }
    private static boolean checkAddress(String[] address){
        String protocolHttp = URL.PROTOCOL_HTTP;
        for (String string : address){
            String answer = connector.request(URL.buildAddress(protocolHttp, string, URL.PING), new PingPacket());
            Log.i(TAG, answer);
            JSONObject parse = JsonParser.parse(answer);
            if (parse != null) {
                String status = String.valueOf(parse.get("status"));
                if (status.equals("success")){
                    return true;
                }
            }

        }
        return false;
    }
}
