package api.sockets.handlers;

import api.sockets.ActiveSubscriptions;
import api.sockets.Subscriber;
import entity.laboratory.subdivisions.vro.VROTurn;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.websocket.Session;
import java.io.IOException;
import java.util.stream.Collectors;

/**
 * Created by szpt_user045 on 18.07.2019.
 */
public class VroHandler extends OnSubscribeHandler {
    final Subscriber subscriber;
    public VroHandler(Subscriber subscriber) {
        this.subscriber = subscriber;
    }

    @Override
    public void handle(Session session) throws IOException {
        JSONArray array = ActiveSubscriptions.pool.getArray();
        array.addAll(dao.getLimitVroTurns().stream().map(parser::toJson).collect(Collectors.toList()));
        JSONObject json = ActiveSubscriptions.pool.getObject();
        json.put(ActiveSubscriptions.ADD, array);
        session.getBasicRemote().sendText(ActiveSubscriptions.prepareMessage(subscriber, json.toJSONString()));
        ActiveSubscriptions.pool.put(json);
    }
}
