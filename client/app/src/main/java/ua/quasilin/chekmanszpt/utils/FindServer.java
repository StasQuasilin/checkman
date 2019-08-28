package ua.quasilin.chekmanszpt.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import org.json.simple.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import ua.quasilin.chekmanszpt.activity.NoConnectionActivity;
import ua.quasilin.chekmanszpt.activity.StartActivity;
import ua.quasilin.chekmanszpt.constants.URL;
import ua.quasilin.chekmanszpt.packets.PingPacket;
import ua.quasilin.chekmanszpt.services.HttpConnector;
import ua.quasilin.chekmanszpt.services.socket.Socket;

/**
 * Created by szpt_user045 on 09.08.2019.
 */

public class FindServer {

    private static final String tag = "FIND SERVER";
    private static final HttpConnector connector = HttpConnector.getConnector();

    public static boolean find(){
        boolean serverFound = checkAddress(URL.INTERNAL_SERVER_ADDRESS);
        if(!serverFound){
            serverFound = checkAddress(URL.EXTERNAL_SERVER_ADDRESS);
        }
        return serverFound;
    }
    private static boolean checkAddress(String[] address){
        for (String url : address){
            boolean check = checkAddress(url);
            if (check){
                return true;
            }
        }
        return false;
    }

    public static boolean checkAddress(String string){
        String protocolHttp = URL.PROTOCOL_HTTP;
        String answer = connector.request(URL.buildAddress(protocolHttp, string, URL.PING), new PingPacket());
        JSONObject parse = JsonParser.parse(answer);
        if (parse != null) {
            String status = String.valueOf(parse.get("status"));
            if (status.equals("success")){
                URL.currentAddress = string;
                return true;
            }
        }
        return false;
    }
}
