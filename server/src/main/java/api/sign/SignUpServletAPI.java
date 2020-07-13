package api.sign;

import api.ServletAPI;
import constants.Branches;
import constants.Constants;
import entity.Person;
import entity.Role;
import entity.User;
import entity.Worker;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.LanguageBase;
import utils.PasswordGenerator;
import utils.UpdateUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@WebServlet(Branches.API.SIGN_UP)
public class SignUpServletAPI extends ServletAPI {

    private final Logger log = Logger.getLogger(SignUpServletAPI.class);
    private final UpdateUtil updateUtil = new UpdateUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if(body != null) {
            log.info(body);

            for (Object o : (JSONArray) body.get("users")) {
                JSONObject json = (JSONObject) o;

                String email = (String) json.get(Constants.EMAIL);
                Role role = Role.valueOf((String) json.get(Constants.ROLE));

                User user = new User();
                user.setUid(getToken());

                boolean autoPassword = true;
                if (json.containsKey("password")){
                    String password = String.valueOf(json.get("password"));
                    if (!password.isEmpty()){
                        autoPassword = false;
                        user.setPassword(new String(Base64.getEncoder().encode(password.getBytes())));
                    }
                }

                if(autoPassword) {
                    user.setPassword(PasswordGenerator.getPassword());
                }

                user.setEmail(email);
                user.setRegistrator(getWorker(req));

                long personId = -1;
                if (json.containsKey("personId")) {
                    personId = (long) json.get("personId");
                }

                Worker worker = new Worker();
                user.setWorker(worker);
                worker.setRole(role);
                worker.setLanguage(LanguageBase.DEFAULT_LANGUAGE);

                Person person;
                if (personId == -1) {
                    person = new Person();
                    person.setSurname((String) json.get(Constants.SURNAME));
                    person.setForename((String) json.get(Constants.FORENAME));
                    person.setPatronymic((String) json.get(Constants.PATRONYMIC));
                    dao.savePerson(person);
                } else {
                    person = dao.getPersonById(personId);
                }

                worker.setPerson(person);
                dao.saveWorker(worker, user);
                updateUtil.onSave(worker);

                write(resp, SUCCESS_ANSWER);

            }
        } else {
            write(resp, EMPTY_BODY);
        }
    }

    public static String getAddress(HttpServletRequest req){
        return req.getRequestURL().toString().replace(req.getRequestURI(), "") + req.getContextPath() + Branches.UI.SING_IN;
    }

    synchronized String getToken(){
        String token = UUID.randomUUID().toString();

        if (dao.getUsersByToken(token).size() > 0){
            return getToken();
        }

        return token;
    }



}
