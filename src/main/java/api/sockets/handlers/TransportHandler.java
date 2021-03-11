package api.sockets.handlers;

import api.sockets.ActiveSubscriptions;
import api.sockets.Subscriber;
import entity.DealType;
import entity.transport.Transportation;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.websocket.Session;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static constants.Constants.UPDATE;

/**
 * Created by szpt_user045 on 11.07.2019.
 */
public class TransportHandler extends OnSubscribeHandler {

    final DealType type;

    public TransportHandler(Subscriber subscriber, DealType type) {
        super(subscriber);
        this.type = type;
    }

    @Override
    public void handle(Session session) throws IOException {
        JSONObject json = pool.getObject();
        JSONArray add = pool.getArray();
        add.addAll(getTransport().stream().map(Transportation::toJson).collect(Collectors.toList()));
        json.put(UPDATE, add);
        session.getBasicRemote().sendText(ActiveSubscriptions.prepareMessage(subscriber, json));
        pool.put(json);
    }

    List<Transportation> getTransport(){
        return dao.getTransportationsByType(type);
    }
}