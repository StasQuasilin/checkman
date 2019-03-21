package utils;

/**
 * Created by szpt_user045 on 12.03.2019.
 */
public class U {
    public static synchronized boolean exist(String value){
        return value != null && !value.isEmpty() && !value.equals("0");
    }

    public static boolean exist (String ... values){
        for (String value : values){
            if (!exist(value)){
                return false;
            }
        }
        return true;
    }
}
