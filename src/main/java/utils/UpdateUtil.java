package utils;

import api.sockets.ActiveSubscriptions;
import api.sockets.Subscriber;
import entity.DealType;
import entity.documents.Deal;
import entity.transport.Transportation;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;

/**
 * Created by szpt_user045 on 11.07.2019.
 */
public class UpdateUtil {
    final JsonPool pool = JsonPool.getPool();
    final ActiveSubscriptions subscriptions = ActiveSubscriptions.getInstance();
    final JsonParser parser = new JsonParser();
    final Logger log = Logger.getLogger(UpdateUtil.class);

    public void onSave(Command command, Deal deal) throws IOException {
        log.info("Command " + command + " for deal " + deal.getId());
        doAction(command, getSubscriber(deal.getType()), parser.toJson(deal));
    }

    Subscriber getSubscriber(DealType type){
        switch (type){
            case buy:
                return Subscriber.DEAL_BUY;
            default:
                return Subscriber.DEAL_SELL;
        }
    }

    public void onSave(Command command, Transportation transportation) throws IOException {
        doAction(command, getSubscriber(transportation.getType()), parser.toJson(transportation));
    }

    void doAction(Command command, Subscriber subscriber, Object ... obj) throws IOException {
        JSONObject json = pool.getObject();
        JSONArray array = pool.getArray();
        for (Object o : obj){
            array.add(o);
        }
        json.put(command.toString(), array);
        subscriptions.send(subscriber, json.toJSONString());
        pool.put(json);
    }

    public enum Command {
        update,
        remove
    }
}
