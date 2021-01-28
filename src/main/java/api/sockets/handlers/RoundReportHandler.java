package api.sockets.handlers;

import api.sockets.ActiveSubscriptions;
import api.sockets.Subscriber;
import entity.weight.RoundReport;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import javax.websocket.Session;
import java.io.IOException;

public class RoundReportHandler extends OnSubscribeHandler {
    @Override
    public void handle(Session session) throws IOException {
        JSONObject json = pool.getObject();
        JSONArray add = pool.getArray();
        for (RoundReport report : dao.getLimitRoundReports()){
            add.add(report.toJson());
        }
        json.put(ADD, add);
        session.getBasicRemote().sendText(ActiveSubscriptions.prepareMessage(subscriber, json));
        pool.put(json);
    }

    public RoundReportHandler(Subscriber subscriber) {
        super(subscriber);
    }
}
