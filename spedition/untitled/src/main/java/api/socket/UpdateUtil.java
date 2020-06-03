package api.socket;

import api.socket.handlers.Handler;

import javax.websocket.Session;
import java.util.ArrayList;

public class UpdateUtil {

    private final Subscribes subscribes = Subscribes.getInstance();

    public void update(SubscribeType type, Object o){
        final Handler handler = subscribes.getHandler(type);
        final ArrayList<Session> sessions = subscribes.getSessions(type);
        System.out.println(sessions);
        if (sessions != null) {
            for (Session session : sessions) {
                handler.send(session, DataType.update, o);
            }
        }
    }
}
