package ua.quasilin.chekmanszpt.utils.socket;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by Kvasik on 30.07.2019.
 */

public class SimpleWebSocket extends WebSocketClient implements IWebSocket {

    SimpleWebSocket(URI serverUri) {
        super(serverUri);
    }

    SimpleWebSocket(String socketAddress) throws URISyntaxException {
        super(new URI(socketAddress));
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("OPEN CONNECTION");
    }

    @Override
    public void onMessage(String message) {
        System.out.println("RECEIVED " + message.toUpperCase());
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {

    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
    }
}
