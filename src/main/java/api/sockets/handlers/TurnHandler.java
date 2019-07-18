package api.sockets.handlers;

import api.sockets.ActiveSubscriptions;
import api.sockets.Subscriber;
import entity.laboratory.LaboratoryTurn;
import entity.production.Turn;
import org.json.simple.JSONArray;

import javax.websocket.Session;
import java.io.IOException;

/**
 * Created by szpt_user045 on 18.07.2019.
 */
public class TurnHandler extends OnSubscribeHandler {
    final Subscriber subscriber;
    public TurnHandler(Subscriber subscriber) {
        this.subscriber = subscriber;
    }

    @Override
    public void handle(Session session) throws IOException {
        JSONArray array = ActiveSubscriptions.pool.getArray();
        for (Turn turn :dao.getLimitTurns()){

        }
    }
}
