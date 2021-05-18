package api.sockets.handlers;

import api.sockets.ActiveSubscriptions;
import api.sockets.Subscribe;
import entity.Worker;
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

    public SealHandler(Subscribe subscribe) {
        super(subscribe);
    }

    @Override
    public void handle(Session session, Worker worker) throws IOException {
        JSONObject json = pool.getObject();
        JSONArray add = pool.getArray();
        add.addAll(dao.getActiveSealsBatches().stream().map(sealBatch -> sealBatch.toJson()).collect(Collectors.toList()));
        json.put(UPDATE, add);
        session.getBasicRemote().sendText(ActiveSubscriptions.prepareMessage(subscribe, json));
        pool.put(json);
    }
}
