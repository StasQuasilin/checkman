package api.sign;

import api.ServletAPI;
import constants.Branches;
import constants.Constants;
import entity.Person;
import entity.Role;
import entity.User;
import entity.Worker;
import entity.answers.ErrorAnswer;
import entity.answers.IAnswer;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.JsonParser;
import utils.PasswordGenerator;
import utils.LanguageBase;
import utils.UpdateUtil;
import utils.email.EmailSender;
import utils.email.RegistratorEmail;

import javax.mail.MessagingException;
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
                worker.setLanguage(LanguageBase.DEFAULT_LANGUAGE);

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
                updateUtil.onSave(worker);

                try {
                    RegistratorEmail.sendEmail(email, getAddress(req), new String(Base64.getDecoder().decode(user.getPassword())));
                    write(resp, answer);
                } catch (MessagingException e) {
                    e.printStackTrace();
                    IAnswer answer = new ErrorAnswer("msg", e.getMessage());
                    JSONObject jsonAnswer = parser.toJson(answer);
                    write(resp, jsonAnswer.toJSONString());
                    pool.put(jsonAnswer);
                }
            }
        } else {
            write(resp, emptyBody);
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
