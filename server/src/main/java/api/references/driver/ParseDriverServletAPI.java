package api.references.driver;

import api.ServletAPI;
import constants.Branches;
import constants.Constants;
import entity.Person;
import entity.Worker;
import entity.documents.LoadPlan;
import entity.notifications.Notification;
import entity.transport.Driver;
import entity.transport.TransportUtil;
import entity.transport.Transportation;
import org.json.simple.JSONObject;
import utils.LanguageBase;
import utils.Parser;
import utils.UpdateUtil;
import utils.VehicleParser;
import utils.answers.SuccessAnswer;
import utils.notifications.Notificator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by szpt_user045 on 12.03.2019.
 */
@WebServlet(Branches.API.PARSE_PERSON)
public class ParseDriverServletAPI extends ServletAPI {

    private final UpdateUtil updateUtil = new UpdateUtil();
    private final Notificator notificator = new Notificator();
    public static final String SUCCESS_PARSE = "notificator.driver.success.parsed";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if(body != null) {
            String key = String.valueOf(body.get(Constants.KEY));
            Driver driver = VehicleParser.parseDriver(key);

            JSONObject json = driver.toJson();
            json.put(IS_NEW, true);
            SuccessAnswer successAnswer = new SuccessAnswer(RESULT, json);
            successAnswer.add(DRIVER, json);

            JSONObject object = successAnswer.toJson();
            write(resp, object.toJSONString());
            pool.put(object);
            if (body.containsKey(Constants.TRANSPORTATION)){
                Transportation transportation = dao.getObjectById(Transportation.class, body.get(TRANSPORTATION));
                TransportUtil.setDriver(transportation, driver);
                dao.save(transportation);
                updateUtil.onSave(transportation);
            }

            Worker worker = getWorker(req);
            notificator.sendNotification(worker, new Notification(
                    String.format(
                            lb.get(worker.getLanguage(), SUCCESS_PARSE),
                            driver.getPerson().getSurname(),
                            driver.getPerson().getForename(),
                            driver.getPerson().getPatronymic())
            ).toJson());
        }
    }
}
