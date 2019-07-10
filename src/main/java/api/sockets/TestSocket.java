package api.sockets;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Kvasik on 10.07.2019.
 */
@ServerEndpoint(value = "/api/test")
public class TestSocket {

    final static ArrayList<Session> sessions = new ArrayList<>();

    @OnOpen
    public void onOpen(Session session) throws IOException {
        sessions.add(session);
        System.out.println("Session #" + session.getId() + " open");
        totalSessions();
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        sessions.remove(session);
        System.out.println("Session #" + session.getId() + " close");
        totalSessions();
    }

    void totalSessions(){
        System.out.println("Sessions count: " + sessions.size());
    }

    @OnMessage
    public void onMessage(Session session, String msg) throws IOException {
        session.getBasicRemote().sendText("Yes, " + msg);
    }

    @OnError
    public void onError(Session session, Throwable t) {
        System.err.println("Error in session #" + session.getId() + ": " + t.getMessage());
    }
}
