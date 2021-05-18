package api.sign;

import api.ServletAPI;
import constants.Branches;
import entity.answers.Answer;
import org.json.simple.JSONObject;
import utils.SignInBox;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@WebServlet(Branches.API.SIGN_IN)
public class SignInAPI extends ServletAPI {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            final Object uuid = body.get(UID);
            final Object hash = body.get(HASH);
            final Object password = body.get(PASSWORD);
            Answer answer = SignInBox.signIn(req, String.valueOf(uuid), String.valueOf(password), String.valueOf(hash));
            write(resp, answer.toJson());
        }
    }
}
