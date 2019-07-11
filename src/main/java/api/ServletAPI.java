package api;

import constants.Constants;
import controllers.IServlet;
import entity.answers.ErrorAnswer;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import utils.JsonParser;
import utils.answers.SuccessAnswer;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by szpt_user045 on 12.03.2019.
 */
public abstract class ServletAPI extends IServlet{
    public final static String EMPTY = "{\"add\":[],\"update\":[],\"remove\":[]}";
    public static final String ADD = "add";
    public static final String UPDATE = "update";
    public static final String REMOVE = "remove";
    public static final String VERSION = "version";
    public static final JsonParser parser = new JsonParser();


//    final JSONParser parser = new JSONParser();
    protected final dbDAO dao = dbDAOService.getDAO();
    public static final String answer = parser.toJson(new SuccessAnswer()).toJSONString();
    public static final String emptyBody = parser.toJson(new ErrorAnswer("msg", "Body parse error")).toJSONString();
    public JSONObject parseBody(HttpServletRequest req){
        try {
            return (JSONObject) parser.parse(req.getReader());
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    PrintWriter writer;
    public void write(HttpServletResponse resp, String text) throws IOException {
        resp.setCharacterEncoding(Constants.ENCODING);
        writer = resp.getWriter();
        writer.write(text);
        writer.close();
    }
}
