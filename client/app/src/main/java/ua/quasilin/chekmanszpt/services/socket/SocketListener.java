package ua.quasilin.chekmanszpt.services.socket;

import android.support.annotation.Nullable;
import android.util.Log;

import okhttp3.*;
import okhttp3.WebSocket;
import ua.quasilin.chekmanszpt.services.MessagesHandler;

/**
 * Created by szpt_user045 on 31.07.2019.
 */

public class SocketListener extends WebSocketListener {

    private static final String TAG = "ws";
    private MessagesHandler handler;

    SocketListener(MessagesHandler handler) {
        this.handler = handler;
    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        super.onOpen(webSocket, response);
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        Log.i(TAG, text);
        handler.handle(text);
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, @Nullable Response response) {
        Log.e(TAG, t.getMessage());
    }

    @Override
    public void onClosed(WebSocket webSocket, int code, String reason) {
        super.onClosed(webSocket, code, reason);
        Log.i(TAG, "Socket closed");
    }
}
