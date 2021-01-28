package utils;

import constants.Constants;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * Created by quasilin on 11.03.2019.
 */
public class PostUtil {

    final static JSONParser parser = new JSONParser();
    final static Logger log = Logger.getLogger(PostUtil.class);

    public static synchronized void write(HttpServletResponse resp, String txt) throws IOException {
        resp.setCharacterEncoding(Constants.ENCODING);
        resp.getWriter().write(txt);
    }
    public synchronized static JSONObject parseBodyJson(HttpServletRequest req) throws IOException {
        try {
            return (JSONObject) parser.parse(req.getReader());
        } catch (ParseException ignored) {}

        return null;
    }
}
