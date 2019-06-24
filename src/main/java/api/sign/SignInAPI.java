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
import utils.SignInBox;
import utils.access.UserBox;
import utils.access.UserData;
import utils.answers.SuccessAnswer;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;

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
            IAnswer answer = SignInBox.signIn(req, String.valueOf(body.get(Constants.UID)), String.valueOf(body.get(Constants.PASSWORD)));
            JSONObject json = JsonParser.toJson(answer);
            PostUtil.write(resp, json.toJSONString());
            log.info(json);
            json.clear();
            body.clear();
        }
    }


}
