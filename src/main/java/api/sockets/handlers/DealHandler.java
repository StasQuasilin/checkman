package api.sockets.handlers;

import api.sockets.ActiveSubscriptions;
import api.sockets.ContentType;
import api.sockets.Subscriber;
import entity.DealType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.JsonParser;

import javax.websocket.Session;
import java.io.IOException;

/**
 * Created by szpt_user045 on 11.07.2019.
 */
public class DealHandler extends OnSubscribeHandler {

    final DealType type;
    final Subscriber subscriber;

    public DealHandler(DealType type, Subscriber subscriber) {
        this.type = type;
        this.subscriber = subscriber;
    }

    @Override
    public void handle(Session session) throws IOException {
        JSONObject json = ActiveSubscriptions.pool.getObject();
        JSONArray add = parser.toDealJson(dao.getDealsByType(type));
        json.put(ActiveSubscriptions.ADD, add);
        session.getBasicRemote().sendText(
                ActiveSubscriptions.prepareMessage(subscriber, json.toJSONString())
        );
        ActiveSubscriptions.pool.put(json);
    }
}
