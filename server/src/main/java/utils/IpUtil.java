package utils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by szpt_user045 on 16.09.2019.
 */
public class IpUtil {
    public static String getIp(HttpServletRequest req) {
        String ip = req.getHeader("X-FORWARDED-FOR");
        if (ip == null){
            ip = req.getRemoteAddr();
        }
        return ip;
    }
}
