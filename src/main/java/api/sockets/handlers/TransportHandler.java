package api.sockets.handlers;

import api.sockets.ActiveSubscriptions;
import api.sockets.Subscribe;
import entity.DealType;
import entity.transport.Transportation;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;
import java.io.IOException;
import java.util.List;

import static constants.Constants.UPDATE;

/**
 * Created by szpt_user045 on 11.07.2019.
 */
public class TransportHandler extends OnSubscribeHandler {

    final DealType type;

    public TransportHandler(Subscribe subscribe, DealType type) {
        super(subscribe);
        this.type = type;
    }

    @Override
    public void handle(Session session) throws IOException {
        JSONObject json = pool.getObject();
        JSONArray add = pool.getArray();

        final RemoteEndpoint.Basic basicRemote = session.getBasicRemote();
        for (Transportation transportation : getTransport()){
            add.add(transportation.toJson());
            json.put(UPDATE, add);
            basicRemote.sendText(ActiveSubscriptions.prepareMessage(subscribe, json));
            add.clear();
        }
        pool.put(json);
    }

    List<Transportation> getTransport(){
        return dao.getTransportationsByType(type);
    }
}
