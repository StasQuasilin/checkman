package utils.access;

import api.sockets.handlers.SessionTimer;
import entity.User;
import entity.UserInfo;
import entity.Worker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by szpt_user045 on 13.03.2019.
 */
public class UserBox {

    private static final UserBox USER_BOX = new UserBox();
    static final SessionTimer sessionTimer = SessionTimer.getInstance();

    public static UserBox getUserBox() {
        return USER_BOX;
    }
    final HashMap<String, UserInfo> users = new HashMap<>();

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
        ArrayList<String> remove = new ArrayList<>();
        for (Map.Entry<String, UserInfo> entry : users.entrySet()){
            UserInfo value = entry.getValue();
            if (value != null) {
                if (value.getUser() != null) {
                    if (value.getUser().getId() == user.getId()) {
                        remove.add(entry.getKey());
                    }
                }
            }
        }
        for (String key : remove){
            users.remove(key);
        }
        users.put(token, new UserInfo(user, ip, sessionId));
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

    public void remove(String token) {
        UserInfo remove = users.remove(token);
        if (sessionTimer != null) {
            sessionTimer.remove(remove.getUser().getWorker());
        }
    }

    public UserInfo getUser(String token) {
        return users.get(token);
    }
}
