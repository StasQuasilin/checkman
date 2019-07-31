package ua.quasilin.chekmanszpt.services.socket;

import android.content.Context;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import ua.quasilin.chekmanszpt.constants.URL;

/**
 * Created by szpt_user045 on 31.07.2019.
 */

public final class Socket {

    private static WebSocket ws;
    private static boolean isConnected = false;
    public static void connect(Context context){
        if (!isConnected) {
            OkHttpClient client = new OkHttpClient.Builder().build();
            Request request = new Request.Builder()
                    .url(URL.SOCKET_ADDRESS)
                    .build();
            SocketListener listener = new SocketListener(context);
            ws = client.newWebSocket(request, listener);
            client.dispatcher().executorService().shutdown();
            isConnected = true;
        }
    }

    public static String send(String text) {
        ws.send(text);
        return text;
    }
}
