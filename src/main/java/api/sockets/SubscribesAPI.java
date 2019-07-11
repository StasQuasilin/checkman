package api.sockets;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by szpt_user045 on 11.07.2019.
 */
@ServerEndpoint(value = "/api/subscribes")
public class SubscribesAPI extends API{
    final static ArrayList<Session> sessions = new ArrayList<>();
    final static Logger logger = Logger.getLogger(SubscribesAPI.class);
    final ActiveSubscriptions activeSubscriptions = ActiveSubscriptions.getInstance();

    @OnOpen
    public void onOpen(Session session, EndpointConfig config){
        sessions.add(session);
        logger.info("Session #" + session.getId() +" open, total sessions: " + sessions.size());
    }

    @OnMessage
    public void onMessage(Session session, String msg) throws ParseException, IOException {
        logger.info("Session #" + session.getId()+ ", message: \'" + msg + "\'");
        JSONObject json = (JSONObject) parseJson(msg);
        if(json.containsKey("action")){
            String action = String.valueOf(json.get("action"));
            switch (action){
                case "subscribe":
                    if (json.containsKey("subscriber")){
                        Subscriber subscriber = Subscriber.valueOf(String.valueOf(json.get("subscriber")));
                        activeSubscriptions.subscribe(subscriber, session);
                    }
                    break;
                case "unsubscribe":
                    if (json.containsKey("subscriber")){
                        Subscriber subscriber = Subscriber.valueOf(String.valueOf(json.get("subscriber")));
                        activeSubscriptions.unSubscribe(subscriber, session);
                    }
                    break;
                default:
                    logger.info("Unhandled action \'" + action + "\'");
                    break;
            }
        }
    }

    @OnClose
    public void onClose(Session session){
        sessions.remove(session);
        logger.info("Session #" + session.getId()+ " close, total sessions: " + sessions.size());
    }

    @OnError
    public void onError(Session session, Throwable t){
        logger.error("Error in session #" + session.getId());
        t.printStackTrace();
    }
}
