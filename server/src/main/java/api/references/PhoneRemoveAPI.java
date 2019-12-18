package api.references;

import api.ServletAPI;
import constants.Branches;
import entity.PhoneNumber;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 17.12.2019.
 */
@WebServlet(Branches.API.PHONE_REMOVE)
public class PhoneRemoveAPI extends ServletAPI {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            System.out.println(body);
            PhoneNumber number = dao.getObjectById(PhoneNumber.class, body.get(ID));
            if (number != null){
                dao.remove(number);
                write(resp, SUCCESS_ANSWER);
            }
        }
    }
}
