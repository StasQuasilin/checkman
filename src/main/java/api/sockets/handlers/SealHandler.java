package api.sockets.handlers;

import api.sockets.ActiveSubscriptions;
import api.sockets.Subscriber;
import entity.seals.Seal;
import entity.seals.SealBatch;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.websocket.Session;
import java.io.IOException;
import java.util.stream.Collectors;

import static constants.Constants.UPDATE;

/**
 * Created by szpt_user045 on 03.02.2020.
 */
public class SealHandler extends OnSubscribeHandler {

    public SealHandler(Subscriber subscriber) {
        super(subscriber);
    }

    @Override
    public void handle(Session session) throws IOException {
        JSONObject json = pool.getObject();
        JSONArray add = pool.getArray();
        add.addAll(dao.getActiveSealsBatches().stream().map(SealBatch::toJson).collect(Collectors.toList()));
        json.put(UPDATE, add);
        session.getBasicRemote().sendText(ActiveSubscriptions.prepareMessage(subscriber, json));
        pool.put(json);
    }
}
