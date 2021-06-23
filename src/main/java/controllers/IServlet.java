package controllers;

import constants.Constants;
import entity.Role;
import entity.Worker;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import utils.JsonParser;
import utils.LanguageBase;
import utils.json.JsonObject;


import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by szpt_user045 on 12.03.2019.
 */
public class IServlet extends HttpServlet implements Constants {

    public static final LanguageBase lb = LanguageBase.getBase();

    public Worker getWorker(HttpServletRequest req){
        return (Worker)req.getSession().getAttribute(Constants.WORKER);
    }
    public Role getRole(HttpServletRequest req){
        String r = String.valueOf(req.getSession().getAttribute(ROLE));
        return Role.valueOf(r);
    }

    public Role getView(HttpServletRequest req){
        final Object attribute = req.getSession().getAttribute(VIEW);
        if (attribute != null){
            return Role.valueOf(String.valueOf(attribute));
        }
        return getRole(req);
    }

    public static final JsonParser parser = new JsonParser();
    @Deprecated
    public synchronized JSONObject parseBody(HttpServletRequest req){
        try {
            return (JSONObject) parser.parse(req.getReader());
        } catch (IOException | ParseException ignore) { }
        return null;
    }
    public JsonObject parseBodyGood(HttpServletRequest request){
        try {
            return new JsonObject((JSONObject) parser.parse(request.getReader()));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            try {
                System.out.println(request.getReader().readLine());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

}
