package api.sockets;

import constants.Branches;
import constants.Constants;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import utils.access.UserBox;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by szpt_user045 on 11.07.2019.
 */
@ServerEndpoint(value = Branches.API.SUBSCRIBER)
public class SubscribesAPI extends API{

    private final Logger logger =Logger.getLogger(SubscribesAPI.class);
    private static SubscribesAPI instance;

    public static SubscribesAPI getInstance() {
        return instance;
    }

    final HashMap<Session, String> sessions = new HashMap<>();
    final static Logger log = Logger.getLogger(SubscribesAPI.class);
    final ActiveSubscriptions activeSubscriptions = ActiveSubscriptions.getInstance();
    final UserBox userBox = UserBox.getInstance();

    public SubscribesAPI(){
        if (instance == null){
            instance = this;
        }
    }
    public void killSession(String token){
        for (Map.Entry<Session, String> entry : sessions.entrySet()){
            if (entry.getValue().equals(token)){
                final Session key = entry.getKey();
                try {
                    key.close(new CloseReason(CloseReason.CloseCodes.NO_STATUS_CODE,"Session close by administrator"));
                    log.info("Session " + token + " kill successfully");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }
    @OnMessage
    public void onMessage(Session session, String msg) throws ParseException, IOException {
        JSONObject json = (JSONObject) parseJson(msg);
        if (json.containsKey("action")) {
            String action = String.valueOf(json.get("action"));
            switch (action) {
                case "subscribe":
                    if (json.containsKey("subscriber")) {
                        Subscriber subscriber = Subscriber.valueOf(String.valueOf(json.get("subscriber")));
                        long worker = (long) json.get("worker");
                        activeSubscriptions.subscribe(subscriber, session, worker);
                    }
                    break;
                case "unsubscribe":
                    if (json.containsKey("subscriber")) {
                        Subscriber subscriber = Subscriber.valueOf(String.valueOf(json.get("subscriber")));
                        activeSubscriptions.unsubscribe(subscriber, session);
                    }
                    break;
                case "ping": {
                    session.getBasicRemote().sendText("Success, " + msg);
                    break;
                }
                case "hello": {
                    final String t = String.valueOf(json.get(Constants.TOKEN));
                    sessions.put(session, t);
                    break;
                }
                default:
                    log.info("Unhandled action '" + action + "'");
                    break;
            }
        } else {
            System.out.println(msg);
        }
    }

    @OnClose
    public void onClose(Session session){
        final String token = sessions.remove(session);
        if (token != null) {
            userBox.isLeft(token);
            log.info("Worker #" + token + " left the building, total sessions: " + sessions.size());
        }
    }

    @OnError
    public void onError(Session session, Throwable t){
        log.error("Error in session #" + session.getId());
        t.printStackTrace();

    }
}
