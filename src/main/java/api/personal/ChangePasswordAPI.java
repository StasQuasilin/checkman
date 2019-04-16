package api.personal;

import api.IAPI;
import constants.Branches;
import entity.User;
import entity.answers.ErrorAnswer;
import entity.answers.IAnswer;
import org.json.simple.JSONObject;
import utils.JsonParser;
import utils.LanguageBase;
import utils.PostUtil;
import utils.answers.SuccessAnswer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 16.04.2019.
 */
@WebServlet(Branches.API.CHANGE_PASSWORD)
public class ChangePasswordAPI extends IAPI {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = PostUtil.parseBodyJson(req);
        IAnswer answer;
        String current = String.valueOf(body.get("current"));
        User user = hibernator.get(User.class, "worker", getWorker(req).getId());
        if (user.getPassword().equals(current)) {
            String password = String.valueOf(body.get("password"));
            user.setPassword(password);
            hibernator.save(user);
            answer = new SuccessAnswer();
        } else {
            answer = new ErrorAnswer();
            answer.add("msg", LanguageBase.getBase().get(user.getLanguage(), "wrong.password"));
        }

        write(resp, JsonParser.toJson(answer).toJSONString());
    }
}
