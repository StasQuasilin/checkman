package api.sockets.handlers;

import api.sockets.ActiveSubscriptions;
import api.sockets.Subscribe;
import entity.DealType;
import entity.Role;
import entity.transport.Transportation;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.websocket.Session;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by szpt_user045 on 11.09.2019.
 */
public class LaboratoryHandler extends OnSubscribeHandler {

    private final DealType type;

    public LaboratoryHandler(Subscribe subscribe, DealType type) {
        super(subscribe);
        this.type = type;
    }

    @Override
    public void handle(Session session, Role view) throws IOException {
        JSONObject json = ActiveSubscriptions.pool.getObject();
        JSONArray add = ActiveSubscriptions.pool.getArray();
        add.addAll(getTransport().stream().map(Transportation::toJson).collect(Collectors.toList()));
        json.put(ADD, add);
        session.getBasicRemote().sendText(ActiveSubscriptions.prepareMessage(subscribe, json));
        ActiveSubscriptions.pool.put(json);
    }

    private List<Transportation> getTransport() {
        return dao.getTransportationByAnalyses(type);
    }
}
