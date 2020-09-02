package ua.s.vasilina.checkman.connection;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;

public final class SocketConnector {

    private static final SocketConnector instance = new SocketConnector();

    public static SocketConnector getInstance() {
        return instance;
    }

    private WebSocket socket;

    public void connect(final String address, ConnectionListener onOpen, ConnectionListener onFailure, ConnectionListener onClose){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        SocketListener listener = new SocketListener(onOpen, onFailure, onClose);
        socket = client.newWebSocket(request, listener);
        client.dispatcher().executorService().shutdown();
    }

    public void send(String message){
        socket.send(message);
    }
}
