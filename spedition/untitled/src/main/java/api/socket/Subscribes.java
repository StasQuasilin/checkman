package api.socket;

import api.socket.handlers.Handler;
import api.socket.handlers.ReportHandler;

import javax.websocket.Session;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Subscribes {

    private static final Subscribes instance = new Subscribes();
    private final HashMap<SubscribeType, ArrayList<Session>> sessions = new HashMap<>();
    private static final HashMap<SubscribeType, Handler> handlers = new HashMap<>();
    static {
        addHandler(new ReportHandler(SubscribeType.reports));
    }

    private Subscribes() {
    }

    public static Subscribes getInstance() {
        return instance;
    }

    static void addHandler(Handler handler){
        handlers.put(handler.getSubscribeType(), handler);
    }

    public void add(Session session, SubscribeType subscribeType) {
        if (!sessions.containsKey(subscribeType)){
            sessions.put(subscribeType, new ArrayList<>());
        }
        sessions.get(subscribeType).add(session);
        final Handler handler = handlers.get(subscribeType);
        try {
            handler.onSubscribe(session);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Session> getSessions(SubscribeType type){
        return sessions.get(type);
    }

    public Handler getHandler(SubscribeType type){
        return handlers.get(type);
    }

    public void remove(Session session, SubscribeType subscribeType) {
        if (sessions.containsKey(subscribeType)){
            final ArrayList<Session> list = sessions.get(subscribeType);
            list.remove(session);
        }
    }

    public void remove(Session session) {
        for(Map.Entry<SubscribeType, ArrayList<Session>> entry : sessions.entrySet()){
            entry.getValue().remove(session);
        }
    }
}
