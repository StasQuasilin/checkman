package api.personal;

import api.API;
import constants.Branches;
import entity.User;
import entity.answers.ErrorAnswer;
import entity.answers.IAnswer;
import org.json.simple.JSONObject;
import utils.JsonParser;
import utils.LanguageBase;
import utils.answers.SuccessAnswer;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 16.04.2019.
 */
@WebServlet(Branches.API.CHANGE_PASSWORD)
public class ChangePasswordAPI extends API {



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            IAnswer answer;
            String current = String.valueOf(body.get("current"));
            User user = dao.getUserByWorker(getWorker(req));
            if (user.getPassword().equals(current)) {
                String password = String.valueOf(body.get("password"));
                user.setPassword(password);
                dao.save(user);
                answer = new SuccessAnswer();
            } else {
                answer = new ErrorAnswer("msg", LanguageBase.getBase().get(user.getWorker().getLanguage(), "wrong.password"));
            }

            write(resp, JsonParser.toJson(answer).toJSONString());
        } else {
            write(resp, emptyBody);
        }
    }
}
