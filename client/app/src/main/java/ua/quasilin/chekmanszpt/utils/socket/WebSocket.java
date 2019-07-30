package ua.quasilin.chekmanszpt.utils.socket;

import android.support.annotation.Nullable;

import okhttp3.Response;
import okhttp3.WebSocketListener;

/**
 * Created by Kvasik on 30.07.2019.
 */

public class WebSocket extends WebSocketListener {
    @Override
    public void onOpen(okhttp3.WebSocket webSocket, Response response) {
        super.onOpen(webSocket, response);
    }

    @Override
    public void onMessage(okhttp3.WebSocket webSocket, String text) {
        super.onMessage(webSocket, text);
    }

    @Override
    public void onClosing(okhttp3.WebSocket webSocket, int code, String reason) {
        super.onClosing(webSocket, code, reason);
    }

    @Override
    public void onFailure(okhttp3.WebSocket webSocket, Throwable t, @Nullable Response response) {
        super.onFailure(webSocket, t, response);
    }
}
