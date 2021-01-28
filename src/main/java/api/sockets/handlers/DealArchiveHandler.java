package api.sockets.handlers;

import api.sockets.ActiveSubscriptions;
import api.sockets.Subscriber;
import entity.DealType;
import entity.documents.Deal;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.websocket.Session;
import java.io.IOException;
import java.util.stream.Collectors;

/**
 * Created by szpt_user045 on 23.07.2019.
 */
public class DealArchiveHandler extends OnSubscribeHandler {
    final DealType type;
    public DealArchiveHandler(DealType type, Subscriber subscriber) {
        super(subscriber);
        this.type = type;
    }

    @Override
    public void handle(Session session) throws IOException {
        JSONArray array = ActiveSubscriptions.pool.getArray();
        array.addAll(dao.getLimitArchiveDeals(type).stream().map(Deal::toJson).collect(Collectors.toList()));
        JSONObject json = ActiveSubscriptions.pool.getObject();
        json.put(ADD, array);
        session.getBasicRemote().sendText(ActiveSubscriptions.prepareMessage(subscriber, json));
        ActiveSubscriptions.pool.put(json);
    }
}
