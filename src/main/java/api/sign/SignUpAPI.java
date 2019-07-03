package api.sign;

import api.API;
import constants.Branches;
import constants.Constants;
import entity.Person;
import entity.Role;
import entity.User;
import entity.Worker;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.PasswordGenerator;
import utils.LanguageBase;
import utils.email.RegistratorEmail;

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
public class SignUpAPI extends API {

    private final Logger log = Logger.getLogger(SignUpAPI.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if(body != null) {
            log.info(body);

            for (Object o : (JSONArray) body.get("users")) {
                JSONObject json = (JSONObject) o;

                String email = (String) json.get(Constants.Person.EMAIL);
                Role role = Role.valueOf((String) json.get(Constants.Person.ROLE));

                User user = new User();
                user.setUid(getToken());
                user.setRole(role);

                user.setPassword(PasswordGenerator.getPassword());
                user.setEmail(email);
                user.setRegistrator(getWorker(req));

                user.setWorker(new Worker());
                long personId = -1;
                if (json.containsKey("personId")) {
                    personId = (long) json.get("personId");
                }

                Worker worker = new Worker();
                user.setWorker(worker);
                worker.setLanguage(LanguageBase.getBase().DEFAULT_LANGUAGE);

                Person person;
                if (personId == -1) {
                    person = new Person();
                    person.setSurname((String) json.get(Constants.Person.SURNAME));
                    person.setForename((String) json.get(Constants.Person.FORENAME));
                    person.setPatronymic((String) json.get(Constants.Person.PATRONYMIC));
                    dao.savePerson(person);
                } else {
                    person = dao.getPersonById(personId);
                }

                worker.setPerson(person);
                dao.saveWorker(worker, user);
                RegistratorEmail.sendEmail(
                        email,
                        getAddress(req),
                        new String(Base64.getDecoder().decode(user.getPassword())));

                write(resp, answer);
            }
        } else {
            write(resp, emptyBody);
        }
    }

    public static String getAddress(HttpServletRequest req){
        return req.getRequestURL().toString().replace(req.getRequestURI(), "") + Branches.UI.SING_IN;
    }



    synchronized String getToken(){
        String token = UUID.randomUUID().toString();

        if (dao.getUsersByToken(token).size() > 0){
            return getToken();
        }

        return token;
    }



}
