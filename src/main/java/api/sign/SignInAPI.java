package api.sign;

import api.API;
import constants.Branches;
import constants.Constants;
import entity.User;
import entity.answers.ErrorAnswer;
import entity.answers.IAnswer;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import utils.JsonParser;
import utils.PostUtil;
import utils.access.UserBox;
import utils.access.UserData;
import utils.answers.SuccessAnswer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@WebServlet(Branches.API.SIGN_IN)
public class SignInAPI extends API {

    private static final Logger log = Logger.getLogger(SignInAPI.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = PostUtil.parseBodyJson(req);
        if (body != null) {
            log.info(body);
            IAnswer answer = signIn(req, String.valueOf(body.get(Constants.UID)), String.valueOf(body.get(Constants.PASSWORD)));
            JSONObject json = JsonParser.toJson(answer);
            PostUtil.write(resp, json.toJSONString());
            log.info(json);
            json.clear();
            body.clear();
        }
    }

    public synchronized static IAnswer signIn(HttpServletRequest req, String uid, String password) throws IOException {
        IAnswer answer;
        log.info("Sign in by user token " + uid);
        User user = hibernator.get(User.class, "uid", uid);

        if (user != null ){
            if (user.getPassword().equals(password)) {
                answer = new SuccessAnswer();
                answer.add("redirect", Branches.UI.HOME);
                log.info("Success, user " + user.getWorker().getPerson().getValue());
                req.getSession().setAttribute("token", UserBox.getUserBox().addUser(new UserData(user, req.getSession())));
                req.getSession().setAttribute("lang", user.getWorker().getLanguage());
                log.info("User language: " + user.getWorker().getLanguage());
                req.getSession().setAttribute("worker", user.getWorker());
                req.getSession().setAttribute("role", user.getRole());
            } else {
                answer = new ErrorAnswer("msd", lb.get(Constants.Languages.WRONG_PASSWORD));
                log.error("Wrong password");
            }
        } else {
            answer = new ErrorAnswer("msd", lb.get(Constants.Languages.NO_USER));
            log.error("User not found");
        }

        return answer;


    }
}
