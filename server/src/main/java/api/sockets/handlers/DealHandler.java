package api.sockets.handlers;

import api.sockets.ActiveSubscriptions;
import api.sockets.Subscriber;
import entity.DealType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.websocket.Session;
import java.io.IOException;

/**
 * Created by szpt_user045 on 11.07.2019.
 */
public class DealHandler extends OnSubscribeHandler {

    final DealType type;

    public DealHandler(DealType type, Subscriber subscriber) {
        super(subscriber);
        this.type = type;
    }

    @Override
    public void handle(Session session) throws IOException {
        JSONObject json = ActiveSubscriptions.pool.getObject();
        JSONArray add = parser.toDealJson(dao.getDealsByType(type));
        json.put(ADD, add);
        session.getBasicRemote().sendText(
                ActiveSubscriptions.prepareMessage(subscriber, json)
        );
        ActiveSubscriptions.pool.put(json);
    }
}
