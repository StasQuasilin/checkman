package api.login;

import api.ServletAPI;
import entity.UserAccess;
import org.json.simple.JSONObject;
import utils.hibernate.dao.UserDAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static constants.Keys.PASSWORD;
import static constants.Keys.PHONE;

public class LoginAPI extends ServletAPI {

    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final JSONObject body = parseBody(req);
        if (body != null){
            String phone = String.valueOf(body.get(PHONE));
            final UserAccess access = userDAO.getUserAccessByPhone(phone);
            if (access != null){
                String password = String.valueOf(body.get(PASSWORD));
                if (access.getPassword().equals(password)){
                    //todo write success
                } else {
                    //todo write wrong password
                }
            } else {
                //todo write no user data
            }
        }
    }
}
