package utils;

import java.util.Base64;
import java.util.Random;

/**
 * Created by szpt_user045 on 16.04.2019.
 */
public class PasswordGenerator {
    static final Random random = new Random();
    static final int LEFT_LIMIT = 63;
    static final int RIGHT_LIMIT = 122;
    static final int TARGET_STRING_LENGTH = 8;

    public static synchronized String getPassword(){
        return getPassword(TARGET_STRING_LENGTH);
    }


    static public String getPassword(int length){
        StringBuilder buffer = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomLimitedInt = LEFT_LIMIT + (int)(random.nextFloat() * (RIGHT_LIMIT - LEFT_LIMIT + 1));
            buffer.append((char) randomLimitedInt);
        }

        return Base64.getEncoder().encodeToString(buffer.toString().getBytes());
    }
}
