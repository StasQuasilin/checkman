package api.sockets.handlers;

import api.sockets.ActiveSubscriptions;
import api.sockets.Subscribe;
import entity.Worker;
import entity.weight.RoundReport;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import javax.websocket.Session;
import java.io.IOException;

public class RoundReportHandler extends OnSubscribeHandler {
    @Override
    public void handle(Session session, Worker worker) throws IOException {
        JSONObject json = pool.getObject();
        JSONArray add = pool.getArray();
        for (RoundReport report : dao.getLimitRoundReports()){
            add.add(report.toJson());
        }
        json.put(ADD, add);
        session.getBasicRemote().sendText(ActiveSubscriptions.prepareMessage(subscribe, json));
        pool.put(json);
    }

    public RoundReportHandler(Subscribe subscribe) {
        super(subscribe);
    }
}
