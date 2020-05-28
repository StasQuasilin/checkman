package api.login;

import api.ServletAPI;
import entity.User;
import entity.UserAccess;
import org.json.simple.JSONObject;
import utils.hibernate.dao.RegistrationDAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static constants.Keys.PASSWORD;
import static constants.Keys.PHONE;

public class RegistrationAPI extends ServletAPI {

    private final RegistrationDAO registrationDAO = new RegistrationDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final JSONObject body = parseBody(req);
        if (body != null){
            String phone = String.valueOf(body.get(PHONE));
            final User user = registrationDAO.getUserByPhone(phone);
            if (user != null){
                String password = String.valueOf(body.get(PASSWORD));
                UserAccess access = new UserAccess();
                access.setUser(user);
                access.setPassword(password);

                registrationDAO.registration(access);
                //todo write success

            } else {
                //todo write no user data
            }
        }
    }
}
