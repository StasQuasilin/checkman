package ua.s.vasilina.checkman.connection;

import android.util.Log;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class SocketListener extends WebSocketListener {

    public static final String TAG = "Socket Listener";

    private final ConnectionListener onOpen;
    private final ConnectionListener onFailure;
    private final ConnectionListener onClosed;

    public SocketListener(ConnectionListener onOpen, ConnectionListener onFailure, ConnectionListener onClosed) {
        this.onOpen = onOpen;
        this.onFailure = onFailure;
        this.onClosed = onClosed;
    }

    @Override
    public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
        super.onOpen(webSocket, response);
        if (onOpen != null){
            onOpen.onChange();
        }
        Log.i(TAG, "Connection open");
    }

    @Override
    public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
        super.onMessage(webSocket, text);
        Log.i(TAG, text);
    }

    @Override
    public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @Nullable Response response) {
        super.onFailure(webSocket, t, response);
        if (onFailure != null){
            onFailure.onChange();
        }
        t.printStackTrace();
    }

    @Override
    public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
        super.onClosed(webSocket, code, reason);
        if (onClosed != null){
            onClosed.onChange();
        }
    }
}
