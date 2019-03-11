package utils;

import constants.Constants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * Created by quasilin on 11.03.2019.
 */
public class PostUtil {
    public static synchronized HashMap<String, String> parseBody(HttpServletRequest req) throws IOException {
        HashMap<String,String> result = new HashMap<>();

        String collect = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        String[] split = collect.split("&");

        for (String s : split){
            String[] sub = s.split("=");
            if (sub.length==2) {
                result.put(sub[0], sub[1]);
            } else {
                result.put(sub[0], null);
            }
        }
        return result;
    }

    public static synchronized void write(HttpServletResponse resp, String txt) throws IOException {
        resp.setCharacterEncoding(Constants.ENCODE);
        resp.getWriter().write(txt);
    }
}
