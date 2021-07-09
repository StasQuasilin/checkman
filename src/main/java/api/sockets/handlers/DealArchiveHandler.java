package api.sockets.handlers;

import api.sockets.ActiveSubscriptions;
import api.sockets.Subscribe;
import entity.DealType;
import entity.Role;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.json.JsonObject;

import javax.websocket.Session;
import java.io.IOException;
import java.util.stream.Collectors;

/**
 * Created by szpt_user045 on 23.07.2019.
 */
public class DealArchiveHandler extends OnSubscribeHandler {
    final DealType type;
    public DealArchiveHandler(DealType type, Subscribe subscribe) {
        super(subscribe);
        this.type = type;
    }

    @Override
    public void handle(Session session, Role view, JsonObject args) throws IOException {
        JSONArray array = ActiveSubscriptions.pool.getArray();
        array.addAll(dao.getLimitArchiveDeals(type).stream().map(deal -> deal.toJson()).collect(Collectors.toList()));
        JSONObject json = ActiveSubscriptions.pool.getObject();
        json.put(ADD, array);
        session.getBasicRemote().sendText(ActiveSubscriptions.prepareMessage(subscribe, json));
        ActiveSubscriptions.pool.put(json);
    }
}
