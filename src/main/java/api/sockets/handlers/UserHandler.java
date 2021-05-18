package api.sockets.handlers;

import api.sockets.ActiveSubscriptions;
import api.sockets.Subscribe;
import entity.UserInfo;
import entity.Worker;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.access.UserBox;

import javax.websocket.Session;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static constants.Constants.*;

/**
 * Created by szpt_user045 on 16.09.2019.
 */
public class UserHandler extends OnSubscribeHandler {

    public UserHandler(Subscribe subscribe) {
        super(subscribe);
    }

    @Override
    public void handle(Session session, Worker worker) throws IOException {
        JSONArray active = pool.getArray();
        final UserBox instance = UserBox.getInstance();
        final List<Worker> workers = dao.getWorkers();

        for (Map.Entry<String, UserInfo> entry : instance.getUsers().entrySet()){
            final UserInfo userInfo = entry.getValue();
            final Worker wo = userInfo.getUser().getWorker();
            workers.remove(wo);
            JSONObject w = wo.toShortJson();
            w.put(TOKEN, entry.getKey());
            w.put(IP, userInfo.getIp());
            w.put(SESSION, userInfo.getSessionId());
            active.add(w);
        }
        for (Worker w : workers){
            final JSONObject json = w.toJson();
            json.put(TOKEN, UUID.randomUUID().toString());
            active.add(json);
        }
        JSONObject object = pool.getObject();
        object.put("update", active);

        session.getBasicRemote().sendText(ActiveSubscriptions.prepareMessage(subscribe, object));
        pool.put(object);
    }
}
