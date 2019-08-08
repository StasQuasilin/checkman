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
import ua.quasilin.chekmanszpt.services.MessagesHandler;

/**
 * Created by szpt_user045 on 31.07.2019.
 */

public class Socket {
    private final Context context;

    public Socket(Context context, MessagesHandler messagesHandler) {
        this.context = context;
        connect(messagesHandler);
    }

    public void disconnect() {
        ws.close(0,null);
    }

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

    private void connect(MessagesHandler messagesHandler){
        if (!isConnected) {
            OkHttpClient client = new OkHttpClient.Builder().build();
            Request request = new Request.Builder()
                    .url(URL.buildWsAddress(URL.SUBSCRIBER))
                    .build();
            SocketListener listener = new SocketListener(messagesHandler);
            ws = client.newWebSocket(request, listener);
            client.dispatcher().executorService().shutdown();
            isConnected = true;
        }
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
