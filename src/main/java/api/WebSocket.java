package api;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by szpt_user045 on 10.07.2019.
 */
public class WebSocket {
    ServerSocket serverSocket;
    public WebSocket(int port) {
        try {
            serverSocket = new ServerSocket(port);
            Socket socket = serverSocket.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
