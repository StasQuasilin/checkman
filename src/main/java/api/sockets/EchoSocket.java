package api.sockets;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import javax.swing.*;

/**
 * Created by szpt_user045 on 31.07.2019.
 */
@ServerEndpoint(value = "/echo")
public class EchoSocket {

    public static void start(){
        timer = new Timer(10000, e -> {
            String text = "In the capital of Ukraine " + LocalTime.now();
            for (Session session : sessions){
                try {
                    session.getBasicRemote().sendText(text);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        timer.start();
    }
    static final ArrayList<Session> sessions = new ArrayList<>();
    static Timer timer;

    @OnOpen
    public void onOpen(Session session) throws IOException {
        sessions.add(session);
        session.getBasicRemote().sendText("Welcome");
    }

    @OnMessage
    public void onMessage(Session session, String message) throws IOException {
        System.out.println(message.toUpperCase() + " from session#" + session.getId());
        session.getBasicRemote().sendText("Yes, " + message);
    }

    @OnClose
    public void onClose(Session session){
        sessions.remove(session);
    }

    public static void stop() {
        timer.stop();
    }
}
