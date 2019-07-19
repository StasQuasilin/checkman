package api.sockets.handlers;

import api.sockets.ActiveSubscriptions;
import api.sockets.Subscriber;
import entity.ApplicationSettings;
import entity.documents.LoadPlan;
import entity.transport.TransportCustomer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.ApplicationSettingsBox;

import javax.websocket.Session;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by szpt_user045 on 16.07.2019.
 */
public class LogisticHandler extends OnSubscribeHandler {

    public LogisticHandler(Subscriber subscriber) {
        super(subscriber);
    }

    ApplicationSettings applicationSettings = ApplicationSettingsBox.getBox().getSettings();
    @Override
    public void handle(Session session) throws IOException {
        if(applicationSettings != null ){

            JSONObject json = ActiveSubscriptions.pool.getObject();
            JSONArray add = ActiveSubscriptions.pool.getArray();
            add.addAll(getTransport().stream().map(parser::toJson).collect(Collectors.toList()));
            json.put(ADD, add);
            session.getBasicRemote().sendText(ActiveSubscriptions.prepareMessage(subscriber, json.toJSONString()));
            ActiveSubscriptions.pool.put(json);
        }
    }

    private List<LoadPlan> getTransport() {
        TransportCustomer customer = applicationSettings.getCustomer();
        return dao.getTransportationsByCustomer(customer);
    }
}
