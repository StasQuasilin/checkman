package api.sign;

import api.ServletAPI;
import constants.Branches;
import entity.answers.IAnswer;
import utils.JsonParser;
import utils.PostUtil;
import utils.answers.SuccessAnswer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 04.04.2019.
 */
@WebServlet(Branches.Sign.LOGOUT)
public class LogoutServletAPI extends ServletAPI {

    final IAnswer answer = new SuccessAnswer();
    {
        answer.add("redirect", Branches.UI.SING_IN);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().removeAttribute("token");

        PostUtil.write(resp, parser.toJson(answer).toJSONString());
    }
}