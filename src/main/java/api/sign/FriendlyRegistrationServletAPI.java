package api.sign;

import api.ServletAPI;
import bot.TelegramBotFactory;
import bot.TelegramNotificator;
import constants.Branches;
import entity.Person;
import entity.Role;
import entity.User;
import entity.Worker;
import org.json.simple.JSONObject;
import utils.LanguageBase;
import utils.PasswordGenerator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 10.06.2019.
 */
@WebServlet(Branches.API.FRIENDLY_REGISTRATION)
public class FriendlyRegistrationServletAPI extends ServletAPI {



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if(body != null) {
            User user = new User();
            Worker worker = new Worker();
            Person person = new Person();
            user.setWorker(worker);
            worker.setPerson(person);
            user.setPassword(PasswordGenerator.getPassword());
            user.setRole(Role.valueOf(String.valueOf(body.get("role"))));

            worker.setLanguage(LanguageBase.DEFAULT_LANGUAGE);

            person.setSurname(String.valueOf(body.get("surname")));
            person.setForename(String.valueOf(body.get("forename")));
            person.setPatronymic(String.valueOf(body.get("patronymic")));

            dao.save(person, worker, user);
            TelegramNotificator notificator = TelegramBotFactory.getTelegramNotificator();
            if (notificator != null) {
                notificator.registrationShow(user);
            }
            write(resp, SUCCESS_ANSWER);
        } else {
            write(resp, EMPTY_BODY);
        }
    }
}
