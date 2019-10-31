package api.sockets.handlers;

import api.sockets.ActiveSubscriptions;
import api.sockets.Subscriber;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.storages.StorageStocks;

import javax.websocket.Session;
import java.io.IOException;
import java.util.stream.Collectors;

/**
 * Created by szpt_user045 on 31.10.2019.
 */
public class StockHandler extends OnSubscribeHandler {

    public StockHandler(Subscriber subscriber) {
        super(subscriber);
    }

    StorageStocks storageStocks = StorageStocks.instance;

    @Override
    public void handle(Session session) throws IOException {
        JSONObject json = pool.getObject();
        json.put(ADD, parser.toStockJson(storageStocks.getStocks()));
        session.getBasicRemote().sendText(ActiveSubscriptions.prepareMessage(subscriber, json));
        pool.put(json);
    }
}
