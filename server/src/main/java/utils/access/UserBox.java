package utils.access;

import entity.User;
import entity.UserInfo;
import org.apache.log4j.Logger;

import javax.servlet.jsp.tagext.TagInfo;
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

    public String updateToken(String oldToken) {
        UserInfo remove = users.remove(oldToken);
        remove.updateActivity();
        final String token = getToken();
        users.put(token, remove);

        return token;
    }

    public HashMap<String, UserInfo> getUsers() {
        return users;
    }

    public String addUser(User user, String ip, String sessionId) {

        final String token = getToken();
        System.out.println("Add: " + token);

        users.put(token, new UserInfo(user, ip, sessionId));
        return token;
    }

    public void remove(String token) {
        users.remove(token);
    }

    public UserInfo getUser(String token) {
        return users.get(token);
    }
}
