package controllers.api.subscribes;

import constants.Apis;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import utils.json.JsonObject;

import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import static constants.Keys.*;

@ServerEndpoint(value = Apis.SUBSCRIBES)
public class EndPoint {

    private final JSONParser parser = new JSONParser();
    private final Subscriber subscriber = Subscriber.getInstance();

    @OnMessage
    public void onMessage(Session session, String message) throws ParseException {
        System.out.println(message);
        final JsonObject jsonObject = new JsonObject(parser.parse(message));
        final String string = jsonObject.getString(ACTION);
        final Subscribes subscribes = Subscribes.valueOf(jsonObject.getString(SUBSCRIBER));
//        final int worker = jsonObject.getInt(WORKER);

        if(string.equals(SUBSCRIBE)){
            subscriber.subscribe(subscribes, session);
        } else if (string.equals(UNSUBSCRIBE)){
        }else {

        }
    }
}
