package api.admin;

import api.ServletAPI;
import constants.Branches;
import entity.Role;
import org.json.simple.JSONObject;
import utils.json.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(Branches.API.CHANGE_ROLE)
public class ChangeRoleAPI extends ServletAPI {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JsonObject body = parseBodyGood(req);
        if(body != null) {
            Role view = Role.valueOf(body.getString(VIEW));
            req.getSession().setAttribute(VIEW, view);
            write(resp, SUCCESS_ANSWER);
        }
    }
}
