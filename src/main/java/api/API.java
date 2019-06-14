package api;

import constants.Constants;
import controllers.IServlet;
import entity.Worker;
import entity.answers.ErrorAnswer;
import jdk.internal.org.objectweb.asm.tree.FieldInsnNode;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import utils.JsonParser;
import utils.LanguageBase;
import utils.PostUtil;
import utils.answers.SuccessAnswer;
import utils.hibernate.Hibernator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 12.03.2019.
 */
public abstract class API extends IServlet{
    final JSONParser parser = new JSONParser();
    public static final String answer = JsonParser.toJson(new SuccessAnswer()).toJSONString();
    public static final String emptyBody = JsonParser.toJson(new ErrorAnswer("msg", "Body parse erro")).toJSONString();
    public JSONObject parseBody(HttpServletRequest req){
        try {
            return (JSONObject) parser.parse(req.getReader());
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void write(HttpServletResponse resp, String text) throws IOException {
        resp.setCharacterEncoding(Constants.ENCODE);
        resp.getWriter().write(text);
    }
}
