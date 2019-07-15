package api.sockets.handlers;

import api.sockets.ActiveSubscriptions;
import api.sockets.Subscriber;
import entity.documents.LoadPlan;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.websocket.Session;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Kvasik on 15.07.2019.
 */
public class LoadPlanHandler extends OnSubscribeHandler {
    final Subscriber subscriber;
    public LoadPlanHandler(Subscriber subscriber) {
        this.subscriber = subscriber;
    }

    @Override
    public void handle(Session session) throws IOException {
        JSONObject json = ActiveSubscriptions.pool.getObject();
        JSONArray add = ActiveSubscriptions.pool.getArray();
        add.addAll(getPlans().stream().map(parser::toJson).collect(Collectors.toList()));
        json.put(ActiveSubscriptions.ADD, add);
        session.getBasicRemote().sendText(ActiveSubscriptions.prepareMessage(subscriber, json.toJSONString()));
        ActiveSubscriptions.pool.put(json);
    }

    private List<LoadPlan> getPlans() {
        return dao.getLoadPlans();
    }
}
