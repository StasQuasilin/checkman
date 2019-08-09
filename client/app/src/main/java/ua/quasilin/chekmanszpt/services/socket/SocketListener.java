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
    private static final int NORMAL_CLOSURE_STATUS = 1000;

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
        String message = t.getMessage();
        Log.e(TAG, message);
        handler.stop();
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        super.onClosing(webSocket, code, reason);
        webSocket.close(code, reason);
        Log.i(TAG, "Socket closing, code " + code);
    }

    @Override
    public void onClosed(WebSocket webSocket, int code, String reason) {
        super.onClosed(webSocket, code, reason);
        Log.i(TAG, "Socket closed, code " + code);
        handler.stop();
    }
}
