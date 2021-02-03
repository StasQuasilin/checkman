package utils.access;

import api.sockets.Subscriber;
import api.sockets.handlers.SessionTimer;
import entity.Client;
import entity.User;
import entity.UserInfo;
import entity.Worker;
import org.json.simple.JSONObject;
import utils.UpdateUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static constants.Constants.TOKEN;

/**
 * Created by szpt_user045 on 13.03.2019.
 */
public class UserBox {

    private static final UserBox USER_BOX = new UserBox();
    static final SessionTimer sessionTimer = SessionTimer.getInstance();

    public static UserBox getInstance() {
        return USER_BOX;
    }
    final HashMap<String, UserInfo> users = new HashMap<>();
    private final UpdateUtil updateUtil = new UpdateUtil();

    public synchronized String getToken(){
        final String token = UUID.randomUUID().toString();
        if (users.containsKey(token)){
            return getToken();
        }
        return token;
    }

    public boolean containsKey(String token) {
        return users.containsKey(token);
    }

    public String updateToken(String oldToken) {
        UserInfo remove = users.remove(oldToken);
        final String token = getToken();
        users.put(token, remove);

        return token;
    }

    public HashMap<String, UserInfo> getUsers() {
        return users;
    }

    public synchronized String addUser(User user, String ip, String sessionId) {
        final String token = getToken();
        final UserInfo userInfo = new UserInfo(Client.web, user, ip, sessionId);
        users.put(token, userInfo);
        final JSONObject json = userInfo.toJson();
        json.put(TOKEN, token);
        updateUtil.doAction(UpdateUtil.Command.update, Subscriber.USERS, json);
        return token;
    }

    public synchronized void remove(Worker worker){
        for (Map.Entry<String, UserInfo> entry : users.entrySet()){
            UserInfo value = entry.getValue();
            if (value.getUser().getWorker().getId() == worker.getId()){
                users.remove(entry.getKey());
            }
        }
    }

    public boolean remove(String token) {
        UserInfo remove = users.remove(token);
        if (remove != null){
            updateUtil.doAction(UpdateUtil.Command.remove, Subscriber.USERS, token);
            return true;
        }
        return false;
    }

    public UserInfo getUser(String token) {
        return users.get(token);
    }

    public void isLeft(String token) {
        remove(token);
    }

    public void onlineWorkers() {
        HashMap<Integer, UserInfo> map = new HashMap<>();
    }
}
