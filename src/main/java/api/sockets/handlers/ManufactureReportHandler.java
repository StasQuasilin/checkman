package api.sockets.handlers;

import api.sockets.ActiveSubscriptions;
import api.sockets.Subscriber;
import entity.reports.ManufactureReport;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.websocket.Session;
import java.io.IOException;
import java.util.stream.Collectors;

/**
 * Created by szpt_user045 on 17.09.2019.
 */
public class ManufactureReportHandler extends OnSubscribeHandler {
    @Override
    public void handle(Session session) throws IOException {
        JSONArray array = pool.getArray();
        array.addAll(dao.getLimitManufactureReports().stream().map(parser::toJson).collect(Collectors.toList()));
        JSONObject json = pool.getObject();
        json.put(ADD, array);
        session.getBasicRemote().sendText(ActiveSubscriptions.prepareMessage(subscriber, json));
        pool.put(json);
    }

    public ManufactureReportHandler(Subscriber subscriber) {
        super(subscriber);
    }
}
