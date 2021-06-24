package api.sockets.handlers;

import api.sockets.ActiveSubscriptions;
import api.sockets.Subscribe;
import entity.Role;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.websocket.Session;
import java.io.IOException;
import java.util.stream.Collectors;

/**
 * Created by szpt_user045 on 18.07.2019.
 */
public class VroHandler extends OnSubscribeHandler {

    public VroHandler(Subscribe subscribe) {
        super(subscribe);
    }

    @Override
    public void handle(Session session, Role view) throws IOException {
        JSONArray array = pool.getArray();
        array.addAll(dao.getLimitVroTurns().stream().map(vroTurn -> vroTurn.toJson()).collect(Collectors.toList()));
        JSONObject json = pool.getObject();
        json.put(ADD, array);
        session.getBasicRemote().sendText(ActiveSubscriptions.prepareMessage(subscribe, json));
        pool.put(json);
    }
}
