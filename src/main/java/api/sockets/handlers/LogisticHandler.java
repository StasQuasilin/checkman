package api.sockets.handlers;

import api.sockets.ActiveSubscriptions;
import api.sockets.Subscribe;
import entity.ApplicationSettings;
import entity.Role;
import entity.transport.TransportCustomer;
import entity.transport.Transportation;
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

    public LogisticHandler(Subscribe subscribe) {
        super(subscribe);
    }

    ApplicationSettings applicationSettings = ApplicationSettingsBox.getBox().getSettings();
    @Override
    public void handle(Session session, Role view) throws IOException {
        if(applicationSettings != null ){
            JSONArray add = ActiveSubscriptions.pool.getArray();
            add.addAll(getTransport().stream().map(transportation -> transportation.toJson()).collect(Collectors.toList()));
            JSONObject json = ActiveSubscriptions.pool.getObject();
            json.put(ADD, add);
            session.getBasicRemote().sendText(ActiveSubscriptions.prepareMessage(subscribe, json));
            ActiveSubscriptions.pool.put(json);
        }
    }

    private List<Transportation> getTransport() {
        TransportCustomer customer = applicationSettings.getCustomer();
        return dao.getTransportationsByCustomer(customer);
    }
}
