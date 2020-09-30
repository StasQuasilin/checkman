package controllers;

import constants.Keys;
import entity.user.User;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import utils.db.DaoService;
import utils.db.dao.user.UserDAO;
import utils.json.JsonObject;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public abstract class Servlet extends HttpServlet {

    private final UserDAO userDAO = DaoService.getUserDAO();
    private JSONParser parser = new JSONParser();

    public JsonObject parseBody(HttpServletRequest request) throws IOException {
        try {
            return new JsonObject(parser.parse(request.getReader()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User getUser(HttpServletRequest req){
        final HttpSession session = req.getSession();
        final Object attribute = session.getAttribute(Keys.USER);
        if (attribute == null){
            final String header = req.getHeader(Keys.USER);
            if (header != null){
                return userDAO.getUserById(header);
            }
        } else {
            return (User) attribute;
        }
        return null;
    }
}
