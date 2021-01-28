package api.admin;

import api.ServletAPI;
import constants.Branches;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(Branches.API.SAVE_USER_DATA)
public class SaveUserAPI extends ServletAPI {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JSONObject body = parseBody(req);
        if (body != null){
            System.out.println(body);

            write(resp, SUCCESS_ANSWER);
        }
    }
}
