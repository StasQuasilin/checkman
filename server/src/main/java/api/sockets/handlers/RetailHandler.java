package api.sockets.handlers;

import api.sockets.ActiveSubscriptions;
import api.sockets.Subscriber;
import entity.DealType;
import entity.transport.Transportation2;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.websocket.Session;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by szpt_user045 on 05.12.2019.
 */
public class RetailHandler extends OnSubscribeHandler {

    DealType type;
    public RetailHandler(Subscriber subscriber) {
        super(subscriber);
    }

    public RetailHandler(Subscriber subscriber, DealType type) {
        super(subscriber);
        this.type = type;
    }

    @Override
    public void handle(Session session) throws IOException {
        JSONObject json = pool.getObject();
        JSONArray add = pool.getArray();
        add.addAll(getTransport().stream().map(Transportation2::toJson).collect(Collectors.toList()));
        json.put(ADD, add);
        session.getBasicRemote().sendText(ActiveSubscriptions.prepareMessage(subscriber, json));
        pool.put(json);
    }

    private List<Transportation2> getTransport() {
        return dao.getTransportations(type);
    }
}
