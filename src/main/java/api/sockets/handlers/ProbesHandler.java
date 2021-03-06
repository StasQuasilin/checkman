package api.sockets.handlers;

import api.sockets.ActiveSubscriptions;
import api.sockets.Subscribe;
import entity.Role;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.json.JsonObject;

import javax.websocket.Session;
import java.io.IOException;
import java.util.stream.Collectors;

import static constants.Constants.UPDATE;

/**
 * Created by szpt_user045 on 19.07.2019.
 */
public class ProbesHandler extends OnSubscribeHandler {
    @Override
    public void handle(Session session, Role view, JsonObject args) throws IOException {
        JSONArray array = ActiveSubscriptions.pool.getArray();
        array.addAll(dao.getLimitProbeTurns().stream().map(probeTurn -> probeTurn.toJson()).collect(Collectors.toList()));
        JSONObject json = ActiveSubscriptions.pool.getObject();
        json.put(UPDATE, array);
        session.getBasicRemote().sendText(ActiveSubscriptions.prepareMessage(subscribe, json));
        ActiveSubscriptions.pool.put(json);
    }

    public ProbesHandler(Subscribe subscribe) {
        super(subscribe);
    }
}
