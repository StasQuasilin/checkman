package ua.quasilin.chekmanszpt.services.socket;

import android.content.Context;

import org.json.simple.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import ua.quasilin.chekmanszpt.constants.URL;
import ua.quasilin.chekmanszpt.entity.ChatContainer;
import ua.quasilin.chekmanszpt.entity.subscribes.Subscriber;
import ua.quasilin.chekmanszpt.packets.JsonPool;
import ua.quasilin.chekmanszpt.services.BackgroundService;
import ua.quasilin.chekmanszpt.services.MessagesHandler;

/**
 * Created by szpt_user045 on 31.07.2019.
 */

public class Socket {

    private final MessagesHandler handler;

    public Socket(MessagesHandler handler) {
        this.handler = handler;
    }

    public void disconnect() {
        if (ws != null) {
            ws.close(1000, null);
        }

    }

    enum Action{
        subscribe,
        unsubscribe
    }
    private static final String ACTION = "action";
    private static final String SUBSCRIBER = "subscriber";
    private static final String WORKER = "worker";
    private static final JsonPool pool = JsonPool.getPool();

    private WebSocket ws;
    private boolean isConnected = false;

    public void connect(){
        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder()
                .url(URL.buildWsAddress(URL.SUBSCRIBER))
                .build();
        ws = client.newWebSocket(request, new SocketListener(handler));
        client.dispatcher().executorService().shutdown();
        isConnected = true;
    }

    public void subscribe(Subscriber subscriber){
        send(Action.subscribe, subscriber);
    }

    private void send(Action action, Subscriber subscriber){
        JSONObject json = pool.getObject();
        json.put(ACTION, action.toString());
        json.put(SUBSCRIBER, subscriber.toString());
        json.put(WORKER, ChatContainer.worker.getId());
        ws.send(json.toJSONString());
        pool.put(json);
    }

    public String send(String text) {
        ws.send(text);
        return text;
    }
}
