package api.sign;

import api.IAPI;
import constants.Branches;
import constants.Constants;
import entity.User;
import entity.answers.ErrorAnswer;
import entity.answers.IAnswer;
import org.json.simple.JSONObject;
import utils.JsonParser;
import utils.PostUtil;
import utils.UserBox;
import utils.answers.SuccessAnswer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@WebServlet(Branches.API.SIGN_IN)
public class SignInAPI extends IAPI{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HashMap<String, String> body = PostUtil.parseBody(req);
        IAnswer answer = signIn(req, resp, body.get(Constants.UID), body.get(Constants.PASSWORD));
        JSONObject json = JsonParser.toJson(answer);
        PostUtil.write(resp, json.toJSONString());
        System.out.println(json);
        json.clear();
    }

    public static IAnswer signIn(HttpServletRequest req, HttpServletResponse resp, String uid, String password) throws IOException {
        IAnswer answer;
        User user = hibernator.get(User.class, "uid", uid);
        if (user != null ){
            if (user.getPassword().equals(password)) {
                answer = new SuccessAnswer();
                answer.add("redirect", Branches.UI.HOME);
                req.getSession().setAttribute("token", UserBox.getUserBox().addUser(user));
                req.getSession().setAttribute("lang", user.getLanguage());
                req.getSession().setAttribute("worker", user.getWorker());
                req.getSession().setAttribute("role", user.getRole());

            } else {
                answer = new ErrorAnswer();
                answer.add("msd", lb.get(Constants.Languages.WRONG_PASSWORD));
            }
        } else {
            answer = new ErrorAnswer();
            answer.add("msd", lb.get(Constants.Languages.NO_USER));
        }

        return answer;


    }
}
