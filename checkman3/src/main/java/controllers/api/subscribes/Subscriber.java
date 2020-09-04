package controllers.api.subscribes;

import controllers.api.subscribes.handlers.DealHandler;
import controllers.api.subscribes.handlers.Handler;
import entity.deals.DealType;

import javax.websocket.Session;
import java.util.HashMap;

public class Subscriber {
    private final HashMap<Subscribes, Session> subscribers = new HashMap<>();
    private final HashMap<Subscribes, Handler> handlers = new HashMap<>();
    {
        addHandler(new DealHandler(Subscribes.deals_buy, DealType.buy));
        addHandler(new DealHandler(Subscribes.deals_sell, DealType.sell));
    }

    private void addHandler(Handler handler) {
        handlers.put(handler.getSubscribes(), handler);
    }

    public void subscribe(Subscribes subscribes, Session session){
        subscribers.put(subscribes, session);
        final Handler handler = handlers.get(subscribes);
        handler.onSubscribe(session);
    }
}
