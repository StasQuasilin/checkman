package api.sockets.handlers;

import api.sockets.ActiveSubscriptions;
import api.sockets.Subscribe;
import entity.DealType;
import entity.Role;
import entity.transport.Transportation;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.hibernate.dao.TransportationDAO;
import utils.json.JsonObject;

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
    boolean withArchive = false;

    public TransportHandler(Subscribe subscribe, DealType type) {
        super(subscribe);
        this.type = type;
    }

    public TransportHandler(Subscribe subscribe, DealType type, boolean withArchive){
        this(subscribe, type);
        this.withArchive = withArchive;
    }

    @Override
    public void handle(Session session, Role view, JsonObject args) throws IOException {
        final int mask = calculateSecureMask(view);
        JSONArray add = pool.getArray();
        for (Transportation transportation : getTransport(args)){
            add.add(transportation.toJson(mask));
        }
        JSONObject json = pool.getObject();
        json.put(UPDATE, add);
        final RemoteEndpoint.Basic basicRemote = session.getBasicRemote();
        basicRemote.sendText(ActiveSubscriptions.prepareMessage(subscribe, json));
        pool.put(json);
    }
    private final TransportationDAO transportationDAO = new TransportationDAO();

    List<Transportation> getTransport(JsonObject args){
        return transportationDAO.getTransportationsByType(type, args);
    }
}
