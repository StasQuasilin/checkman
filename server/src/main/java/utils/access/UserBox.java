package utils.access;

import entity.User;
import entity.UserInfo;
import org.apache.log4j.Logger;

import java.time.LocalTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by szpt_user045 on 13.03.2019.
 */
public class UserBox {

    private static final UserBox USER_BOX = new UserBox();

    public static UserBox getUserBox() {
        return USER_BOX;
    }
    final HashMap<String, UserInfo> users = new HashMap<>();

    public String getToken(){
        final String token = UUID.randomUUID().toString();
        if (users.containsKey(token)){
            return getToken();
        }
        return token;
    }

    public boolean containsKey(String token) {
        return users.containsKey(token);
    }

    public String updateToken(String oldToken, String ip) {
        final String token = getToken();
        users.put(token, users.remove(oldToken));
        return token;
    }

    public Collection<UserInfo> getUsers(){
        return users.values();
    }

    public String addUser(User user, String ip) {
        final String token = getToken();
        users.put(token, new UserInfo(user, ip));
        return token;
    }
}
