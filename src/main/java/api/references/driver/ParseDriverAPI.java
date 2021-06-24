package api.references.driver;

import api.ServletAPI;
import constants.Branches;
import constants.Constants;
import entity.Person;
import entity.Worker;
import entity.notifications.Notification;
import entity.transport.Driver;
import entity.transport.TransportUtil;
import entity.transport.Transportation;
import org.json.simple.JSONObject;
import utils.U;
import utils.UpdateUtil;
import utils.VehicleParser;
import utils.answers.SuccessAnswer;
import utils.notifications.SomeNotificator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 12.03.2019.
 */
@WebServlet(Branches.API.PARSE_PERSON)
public class ParseDriverAPI extends ServletAPI {

    private final UpdateUtil updateUtil = new UpdateUtil();
    private final SomeNotificator notificator = new SomeNotificator();
    public static final String SUCCESS_PARSE = "notificator.driver.success.parsed";
    private final VehicleParser vehicleParser = new VehicleParser();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if(body != null) {
            String key = String.valueOf(body.get(Constants.KEY));
            Driver driver = vehicleParser.parseDriver(key);

            JSONObject json = driver.toJson();
            json.put(IS_NEW, true);
            SuccessAnswer successAnswer = new SuccessAnswer(RESULT, json);
            successAnswer.add(DRIVER, json);

            JSONObject object = successAnswer.toJson();
            write(resp, object.toJSONString());
            pool.put(object);
            if (body.containsKey(TRANSPORTATION)){
                Transportation transportation = dao.getObjectById(Transportation.class, body.get(TRANSPORTATION));
                TransportUtil.setDriver(transportation, driver, false);
                dao.save(transportation);
                updateUtil.onSave(transportation);
            }

            Worker worker = getWorker(req);
            final Person person = driver.getPerson();
            notificator.sendNotification(worker, new Notification(
                    String.format(
                            lb.get(worker.getLanguage(), SUCCESS_PARSE),
                            orEmpty(person.getSurname()),
                            orEmpty(person.getForename()),
                            orEmpty(person.getPatronymic()))
            ).toJson());
        }
    }

    private String orEmpty(String value) {
        if(U.exist(value)){
            return value;
        }
        return EMPTY;
    }
}
