package api.sockets.handlers;

import api.sockets.ActiveSubscriptions;
import api.sockets.Subscriber;
import entity.DealType;
import entity.documents.LoadPlan;
import entity.transport.Transportation;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.websocket.Session;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by szpt_user045 on 11.07.2019.
 */
public class TransportHandler extends OnSubscribeHandler {

    final Subscriber subscriber;
    final DealType type;

    public TransportHandler(Subscriber subscriber, DealType type) {
        this.subscriber = subscriber;
        this.type = type;
    }

    @Override
    public void handle(Session session) throws IOException {
        JSONObject json = ActiveSubscriptions.pool.getObject();
        JSONArray add = ActiveSubscriptions.pool.getArray();
        add.addAll(getTransport().stream().map(parser::toLogisticJson).collect(Collectors.toList()));
        json.put(ActiveSubscriptions.ADD, add);
        session.getBasicRemote().sendText(ActiveSubscriptions.prepareMessage(subscriber, json.toJSONString()));
        ActiveSubscriptions.pool.put(json);
    }

    List<LoadPlan> getTransport(){
        if (type == null) {
            return dao.getActiveTransportations(null);
        } else {
            return dao.getLoadPlansByDealType(type);
        }
    }
}
