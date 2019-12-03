package api.sockets.handlers;

import api.sockets.ActiveSubscriptions;
import api.sockets.Subscriber;
import entity.DealType;
import entity.JsonAble;
import entity.deal.Contract;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.websocket.Session;
import java.io.IOException;
import java.util.stream.Collectors;

/**
 * Created by szpt_user045 on 11.07.2019.
 */
public class ContractHandler extends OnSubscribeHandler {

    final DealType type;

    public ContractHandler(DealType type, Subscriber subscriber) {
        super(subscriber);
        this.type = type;
    }

    @Override
    public void handle(Session session) throws IOException {
        JSONObject json = pool.getObject();
        JSONArray array = pool.getArray();
        array.addAll(dao.getContractsByType(type).stream().map(Contract::toJson).collect(Collectors.toList()));
        json.put(ADD, array);
        session.getBasicRemote().sendText(ActiveSubscriptions.prepareMessage(subscriber, json));
        pool.put(json);
    }
}
