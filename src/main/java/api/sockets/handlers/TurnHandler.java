package api.sockets.handlers;

import api.sockets.ActiveSubscriptions;
import api.sockets.Subscriber;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.websocket.Session;
import java.io.IOException;
import java.util.stream.Collectors;

/**
 * Created by szpt_user045 on 18.07.2019.
 */
public class TurnHandler extends OnSubscribeHandler {

    public TurnHandler(Subscriber subscriber) {
        super(subscriber);
    }

    @Override
    public void handle(Session session) throws IOException {
        JSONArray array = ActiveSubscriptions.pool.getArray();
        array.addAll(dao.getLimitLaboratoryTurn().stream().map(parser::toJson).collect(Collectors.toList()));
        JSONObject json = ActiveSubscriptions.pool.getObject();
        json.put(ADD, array);
        session.getBasicRemote().sendText(ActiveSubscriptions.prepareMessage(subscriber, json));
        ActiveSubscriptions.pool.put(json);
    }
}
