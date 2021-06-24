package api.sockets.handlers;

import api.sockets.ActiveSubscriptions;
import api.sockets.Subscribe;
import entity.DealType;
import entity.Role;
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

    public ContractHandler(DealType type, Subscribe subscribe) {
        super(subscribe);
        this.type = type;
    }

    @Override
    public void handle(Session session, Role view) throws IOException {
        JSONObject json = pool.getObject();
        JSONArray array = pool.getArray();
        array.addAll(dao.getContractsByType(type).stream().map(contract -> contract.toJson()).collect(Collectors.toList()));
        json.put(ADD, array);
        session.getBasicRemote().sendText(ActiveSubscriptions.prepareMessage(subscribe, json));
        pool.put(json);
    }
}
