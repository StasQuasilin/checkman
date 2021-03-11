package api.sockets.handlers;

import api.sockets.ActiveSubscriptions;
import api.sockets.Subscriber;
import entity.laboratory.subdivisions.extraction.ExtractionTurn;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.websocket.Session;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by szpt_user045 on 18.07.2019.
 */
public class ExtractionHandler extends OnSubscribeHandler {

    public ExtractionHandler(Subscriber subscriber) {
        super(subscriber);
    }

    @Override
    public void handle(Session session) throws IOException {
        JSONObject json = pool.getObject();
        JSONArray array = pool.getArray();
        List<ExtractionTurn> turns = dao.getLimitExtractionTurns();
        for (ExtractionTurn turn : turns){
            array.add(turn.toJson());
        }
        json.put(ADD, array);
        session.getBasicRemote().sendText(ActiveSubscriptions.prepareMessage(subscriber, json));
        pool.put(json);
    }
}