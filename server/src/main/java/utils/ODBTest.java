package utils;

import entity.User;
import utils.hibernate.Hibernator;

import java.util.Base64;

/**
 * Created by szpt_user045 on 19.12.2019.
 */
public class ODBTest {

    static Hibernator hibernator = Hibernator.getInstance();

    public static void main(String[] args) {

        for (User user : hibernator.query(User.class, null)){
            final String string = user.getPassword();
            final String password = new String(Base64.getDecoder().decode(string));

        }
    }
}
