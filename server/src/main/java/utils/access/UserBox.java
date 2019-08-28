package utils.access;

import entity.User;
import org.apache.log4j.Logger;

import java.time.LocalTime;
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
    final HashMap<String, User> users = new HashMap<>();

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

    public String updateToken(String oldToken) {
        final String token = getToken();
        users.put(token, users.remove(oldToken));
        return token;
    }

    public String addUser(User user) {
        final String token = getToken();
        users.put(token, user);
        return token;
    }
}
