package api.sockets;

import api.sockets.handlers.DealHandler;
import api.sockets.handlers.OnSubscribeHandler;
import api.sockets.handlers.TransportHandler;
import entity.DealType;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import utils.JsonPool;

import javax.websocket.Session;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by szpt_user045 on 11.07.2019.
 */
public class ActiveSubscriptions {

    private static ActiveSubscriptions instance = new ActiveSubscriptions();
    private final Logger log = Logger.getLogger(ActiveSubscriptions.class);
    final HashMap<Subscriber, ArrayList<Session>> subscribes = new HashMap<>();
    final HashMap<Subscriber, OnSubscribeHandler> handlers = new HashMap<>();
    public static final JsonPool pool = JsonPool.getPool();
    public static final String ADD = "add";
    public static final String TYPE = "type";
    private static final String DATA = "data";

    private ActiveSubscriptions(){
        for(Subscriber s : Subscriber.values()){
            subscribes.put(s, new ArrayList<>());
        }
        handlers.put(Subscriber.DEAL_BUY, new DealHandler(DealType.buy, Subscriber.DEAL_BUY));
        handlers.put(Subscriber.DEAL_SELL, new DealHandler(DealType.sell, Subscriber.DEAL_SELL));
        handlers.put(Subscriber.TRANSPORT_BUY, new TransportHandler(Subscriber.TRANSPORT_BUY, DealType.buy));
        handlers.put(Subscriber.TRANSPORT_SELL, new TransportHandler(Subscriber.TRANSPORT_SELL, DealType.sell));
    }

    public static ActiveSubscriptions getInstance() {
        return instance;
    }

    public void subscribe(Subscriber sub, Session session) throws IOException {
        subscribes.get(sub).add(session);
        if (handlers.containsKey(sub)) {
            handlers.get(sub).handle(session);
        }
        log.info("Session #" + session.getId() + ", subscribe on " + sub.toString());
    }
    public void unSubscribe(Subscriber sub, Session session){
        subscribes.get(sub).remove(session);
    }
    public void send(Subscriber sub, String txt) throws IOException {
        txt = prepareMessage(sub, txt);
        for (Session session : subscribes.get(sub)){
            if (session.isOpen()){
                session.getBasicRemote().sendText(txt);
            }
        }
    }

    public static String prepareMessage(Subscriber type, String msg){
        JSONObject object = pool.getObject();
        object.put(TYPE, type.toString());
        object.put(DATA, msg);
        return object.toJSONString();
    }
}
