package api;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

public abstract class ServletAPI extends HttpServlet {

    public void write(HttpServletResponse resp, String msg) throws IOException {
        resp.getWriter().write(msg);
    }

    JSONParser parser = new JSONParser();
    public final JSONObject parseBody(HttpServletRequest req) throws IOException {
        BufferedReader reader = req.getReader();
        try {
            return (JSONObject) parser.parse(reader);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
