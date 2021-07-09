package api.sockets.handlers;

import api.sockets.ActiveSubscriptions;
import api.sockets.Subscribe;
import entity.DealType;
import entity.Role;
import entity.documents.Deal;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.json.JsonObject;

import javax.websocket.Session;
import java.io.IOException;

import static constants.Constants.UPDATE;

/**
 * Created by szpt_user045 on 11.07.2019.
 */
public class DealHandler extends OnSubscribeHandler {

    final DealType type;

    public DealHandler(DealType type, Subscribe subscribe) {
        super(subscribe);
        this.type = type;
    }

    @Override
    public void handle(Session session, Role view, JsonObject args) throws IOException {
        JSONObject json = pool.getObject();
        JSONArray add = pool.getArray();
        for (Deal deal : dao.getDealsByType(type)){
            add.add(deal.toJson());
        }
        json.put(UPDATE, add);
        session.getBasicRemote().sendText(
                ActiveSubscriptions.prepareMessage(subscribe, json)
        );
        pool.put(json);
    }
}
