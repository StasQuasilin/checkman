package controllers.api.subscribes.handlers;

import constants.Keys;
import controllers.api.subscribes.Subscribes;
import org.json.simple.JSONObject;

import javax.websocket.Session;

import java.io.IOException;

import static constants.Keys.ADD;
import static constants.Keys.SUBSCRIBER;

public abstract class Handler {
    private final Subscribes subscribes;

    public Handler(Subscribes subscribes) {
        this.subscribes = subscribes;
    }

    public Subscribes getSubscribes() {
        return subscribes;
    }

    protected abstract Object getSubscribeData();

    public void onSubscribe(Session session) {
        JSONObject object = new JSONObject();
        object.put(SUBSCRIBER, subscribes.toString());
        final JSONObject data = new JSONObject();
        data.put(ADD, getSubscribeData());
        object.put(Keys.DATA, data);
        try {
            session.getBasicRemote().sendText(object.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
