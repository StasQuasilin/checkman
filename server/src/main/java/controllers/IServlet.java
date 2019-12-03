package controllers;

import constants.Branches;
import constants.Constants;
import entity.Role;
import entity.Worker;
import jdk.nashorn.internal.runtime.regexp.joni.ast.ConsAltNode;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import utils.JsonParser;
import utils.LanguageBase;
import utils.PostUtil;
import utils.U;
import utils.hibernate.Hibernator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 12.03.2019.
 */
public class IServlet extends HttpServlet implements Constants {





    public static final LanguageBase lb = LanguageBase.getBase();
    protected static final String CUSTOMERS = "customers";

    public Worker getWorker(HttpServletRequest req){
        return (Worker)req.getSession().getAttribute(Constants.WORKER);
    }
    public Role getRole(HttpServletRequest req){
        String r = String.valueOf(req.getSession().getAttribute(ROLE));
        return Role.valueOf(r);
    }

    public static final JsonParser parser = new JsonParser();
    public JSONObject parseBody(HttpServletRequest req){
        try {
            return (JSONObject) parser.parse(req.getReader());
        } catch (IOException | ParseException ignore) { }
        return null;
    }

}
