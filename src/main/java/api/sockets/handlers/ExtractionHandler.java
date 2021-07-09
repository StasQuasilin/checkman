package api.sockets.handlers;

import api.sockets.ActiveSubscriptions;
import api.sockets.Subscribe;
import entity.Role;
import entity.laboratory.subdivisions.extraction.ExtractionTurn;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.json.JsonObject;

import javax.websocket.Session;
import java.io.IOException;
import java.util.List;

/**
 * Created by szpt_user045 on 18.07.2019.
 */
public class ExtractionHandler extends OnSubscribeHandler {

    public ExtractionHandler(Subscribe subscribe) {
        super(subscribe);
    }

    @Override
    public void handle(Session session, Role view, JsonObject args) throws IOException {
        JSONObject json = pool.getObject();
        JSONArray array = pool.getArray();
        List<ExtractionTurn> turns = dao.getLimitExtractionTurns();
        for (ExtractionTurn turn : turns){
            array.add(turn.toJson());
        }
        json.put(ADD, array);
        session.getBasicRemote().sendText(ActiveSubscriptions.prepareMessage(subscribe, json));
        pool.put(json);
    }
}
