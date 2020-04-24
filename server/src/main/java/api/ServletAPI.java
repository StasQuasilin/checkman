package api;

import constants.Constants;
import controllers.IServlet;
import entity.answers.ErrorAnswer;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import utils.JsonParser;
import utils.JsonPool;
import utils.answers.SuccessAnswer;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by szpt_user045 on 12.03.2019.
 */
public abstract class ServletAPI extends IServlet{

    public static final JsonPool pool = JsonPool.getPool();

    protected final dbDAO dao = dbDAOService.getDAO();
    public static final String SUCCESS_ANSWER = parser.toJson(new SuccessAnswer()).toJSONString();
    public static final String EMPTY_BODY = parser.toJson(new ErrorAnswer("msg", "Body parse error")).toJSONString();

    PrintWriter writer;
    public void write(HttpServletResponse resp, String text) throws IOException {
        resp.setCharacterEncoding(Constants.ENCODING);
        writer = resp.getWriter();
        writer.write(text);
        writer.close();
    }
    public void write(HttpServletResponse resp, JSONObject jsonObject) throws IOException {
        write(resp, jsonObject.toJSONString());
        pool.put(jsonObject);
    }


}
