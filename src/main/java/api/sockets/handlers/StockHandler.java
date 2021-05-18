package api.sockets.handlers;

import api.sockets.ActiveSubscriptions;
import api.sockets.Subscribe;
import entity.Worker;
import org.json.simple.JSONObject;
import utils.storages.StorageUtil;

import javax.websocket.Session;
import java.io.IOException;

/**
 * Created by szpt_user045 on 31.10.2019.
 */
public class StockHandler extends OnSubscribeHandler {

    public StockHandler(Subscribe subscribe) {
        super(subscribe);
    }

    StorageUtil storageUtil = new StorageUtil();


    @Override
    public void handle(Session session, Worker worker) throws IOException {
        JSONObject json = pool.getObject();
        json.put(ADD, parser.toStockJson(storageUtil.getStocks()));
        session.getBasicRemote().sendText(ActiveSubscriptions.prepareMessage(subscribe, json));
        pool.put(json);
    }
}
