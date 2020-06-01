package api.login;

import api.ServletAPI;
import entity.ErrorAnswer;
import entity.ServerAnswer;
import entity.SuccessAnswer;
import entity.UserAccess;
import org.json.simple.JSONObject;
import utils.hibernate.dao.UserDAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static constants.Keys.*;

public class LoginAPI extends ServletAPI {

    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final JSONObject body = parseBody(req);
        if (body != null){
            String phone = String.valueOf(body.get(PHONE));
            final UserAccess access = userDAO.getUserAccessByPhone(phone);
            ServerAnswer answer;
            if (access != null){
                String password = String.valueOf(body.get(PASSWORD));
                if (access.getPassword().equals(password)){
                    answer = new SuccessAnswer();
                    answer.addParam(TOKEN, access.getToken());
                } else {
                    answer = new ErrorAnswer();
                    answer.addParam(REASON, WRONG_PASSWORD);
                }
            } else {
                answer = new ErrorAnswer();
                answer.addParam(REASON, NOT_FOUND);
            }
        }
    }
}
