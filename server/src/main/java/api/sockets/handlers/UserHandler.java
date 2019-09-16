package api.sockets.handlers;

import api.sockets.ActiveSubscriptions;
import api.sockets.Subscriber;
import entity.User;
import entity.UserInfo;
import entity.Worker;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.access.UserBox;

import javax.websocket.Session;
import java.io.IOException;
import java.util.stream.Collectors;

/**
 * Created by szpt_user045 on 16.09.2019.
 */
public class UserHandler extends OnSubscribeHandler {

    private final UserBox userBox = UserBox.getUserBox();

    public UserHandler(Subscriber subscriber) {
        super(subscriber);
    }

    @Override
    public void handle(Session session) throws IOException {
        JSONArray active = pool.getArray();
        JSONObject w;
        for (UserInfo info : userBox.getUsers()){
            w = parser.toJson(info.getUser().getWorker());
            w.put("ip", info.getIp());
            active.add(w);
        }

        JSONArray all = pool.getArray();
        all.addAll(dao.getWorkers().stream().map(parser::toJson).collect(Collectors.toList()));
        JSONObject json =pool.getObject();
        json.put("active", active);
        json.put("all", all);
        JSONObject object = pool.getObject();
        object.put(ADD, json);
        session.getBasicRemote().sendText(ActiveSubscriptions.prepareMessage(subscriber, object));
        pool.put(object);
    }
}
