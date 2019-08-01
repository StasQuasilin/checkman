package ua.quasilin.chekmanszpt.services.socket;

import android.content.Context;

import org.json.simple.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import ua.quasilin.chekmanszpt.constants.URL;
import ua.quasilin.chekmanszpt.entity.subscribes.Subscriber;
import ua.quasilin.chekmanszpt.packets.JsonPool;

/**
 * Created by szpt_user045 on 31.07.2019.
 */

public final class Socket {
    enum Action{
        subscribe,
        unsubscribe
    }
    private static final String ACTION = "action";
    private static final String SUBSCRIBER = "subscriber";
    private static final String WORKER = "worker";
    private static final JsonPool pool = JsonPool.getPool();

    private static WebSocket ws;
    private static boolean isConnected = false;
    public static void connect(Context context){
        if (!isConnected) {
            OkHttpClient client = new OkHttpClient.Builder().build();
            Request request = new Request.Builder()
                    .url(URL.buildWsAddress(URL.SUBSCRIBER))
                    .build();
            SocketListener listener = new SocketListener(context);
            ws = client.newWebSocket(request, listener);
            client.dispatcher().executorService().shutdown();
            isConnected = true;
        }
    }

    public static void subscribe(Subscriber subscriber){
        send(Action.subscribe, subscriber);
    }

    private static void send(Action action, Subscriber subscriber){
        JSONObject json = pool.getObject();
        json.put(ACTION, action.toString());
        json.put(SUBSCRIBER, subscriber.toString());
        json.put(WORKER, 1);
        String result = json.toJSONString();
        System.out.println(result);
        pool.put(json);
        ws.send(result);
    }



    public static String send(String text) {
        ws.send(text);
        return text;
    }
}
