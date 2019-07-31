package ua.quasilin.chekmanszpt.services.socket;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import okhttp3.*;
import okhttp3.WebSocket;
import ua.quasilin.chekmanszpt.services.MessagesHandler;

/**
 * Created by szpt_user045 on 31.07.2019.
 */

public class SocketListener extends WebSocketListener {

    private final Context context;

    SocketListener(Context context) {
        this.context = context;
    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        super.onOpen(webSocket, response);
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        Log.i("WS", text.toUpperCase());
        MessagesHandler.getHandler().handle(text);
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, @Nullable Response response) {
        Log.e("WS", t.getMessage());
    }
}
