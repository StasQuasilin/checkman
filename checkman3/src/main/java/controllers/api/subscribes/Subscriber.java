package controllers.api.subscribes;

import com.google.common.eventbus.Subscribe;
import controllers.api.subscribes.handlers.DealHandler;
import controllers.api.subscribes.handlers.Handler;
import controllers.api.subscribes.handlers.TransportationHandler;
import entity.deals.DealType;

import javax.websocket.Session;
import java.util.HashMap;

public class Subscriber {

    private static final Subscriber instance = new Subscriber();

    public static Subscriber getInstance() {
        return instance;
    }

    private final HashMap<Subscribes, Session> subscribers = new HashMap<>();
    private final HashMap<Subscribes, Handler> handlers = new HashMap<>();
    {
        addHandler(new DealHandler(Subscribes.deals_buy, DealType.buy));
        addHandler(new DealHandler(Subscribes.deals_sell, DealType.sell));
        addHandler(new TransportationHandler());
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
