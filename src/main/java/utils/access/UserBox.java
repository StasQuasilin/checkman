package utils.access;

import org.apache.log4j.Logger;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by szpt_user045 on 13.03.2019.
 */
public class UserBox {

    private final Logger log = Logger.getLogger(UserBox.class);
    private static final UserBox USER_BOX = new UserBox();

    public static UserBox getUserBox() {
        return USER_BOX;
    }

    final HashMap<String, UserData> users = new HashMap<>();
    private int currentMinute = -1;
    private int successAccess = 0;
    private int wrongAccess = 0;

    public String getToken(){
//        tick(1, 0);
        final String token = UUID.randomUUID().toString();
        if (users.containsKey(token)){
            return getToken();
        }
        return token;
    }

    public boolean containsKey(String token) {
        //        if (!containsKey){
//            tick(0, 1);
//        }
        return users.containsKey(token);
    }

    public String updateToken(String oldToken) {
        final String token = getToken();
        users.put(token, users.remove(oldToken));
        return token;
    }

    private void tick(int success, int wrong){
        if (currentMinute != LocalTime.now().getMinute()){
            currentMinute = LocalTime.now().getMinute();
            if (successAccess > 0 ){
                log.info(String.format("Generate %s access token", successAccess));
                successAccess = 0;
            }
            if (wrongAccess > 0 ){
                log.info(String.format("Wrong %s access token", wrongAccess));
                wrongAccess = 0;
            }
        } else {
            successAccess += success;
            wrongAccess += wrong;
        }
    }

    public String addUser(UserData userData) {
        final String token = getToken();
        users.put(token, userData);
        return token;
    }
}
