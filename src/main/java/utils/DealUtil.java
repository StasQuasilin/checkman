package utils;

import api.sockets.ActiveSubscriptions;
import api.sockets.Subscriber;
import entity.DealType;
import entity.documents.Deal;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;

/**
 * Created by szpt_user045 on 11.07.2019.
 */
public class DealUtil {
    private static final String UPDATE = "update";
    final JsonPool pool = JsonPool.getPool();
    final ActiveSubscriptions subscriptions = ActiveSubscriptions.getInstance();
    final JsonParser parser = new JsonParser();

    public void save(Deal deal) throws IOException {
        JSONObject json = pool.getObject();
        JSONArray array = pool.getArray();
        array.add(parser.toJson(deal));
        json.put(UPDATE, array);
        Subscriber subscriber = deal.getType() == DealType.buy ? Subscriber.DEAL_BUY : Subscriber.DEAL_SELL;
        subscriptions.send(subscriber, json.toJSONString());
        pool.put(json);

    }
}
