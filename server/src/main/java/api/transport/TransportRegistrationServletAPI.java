package api.transport;

import api.ServletAPI;
import bot.BotFactory;
import bot.Notificator;
import constants.Branches;
import constants.Constants;
import entity.transport.ActionTime;
import entity.transport.Transportation;
import entity.weight.Weight;
import org.json.simple.JSONObject;
import utils.UpdateUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;

/**
 * Created by Kvasik on 29.08.2019.
 */
@WebServlet(Branches.API.TRANSPORT_REGISTRATION)
public class TransportRegistrationServletAPI extends ServletAPI {

    private final UpdateUtil updateUtil = new UpdateUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            Object transportationId = body.get(Constants.TRANSPORTATION);
            Transportation transportation = dao.getTransportationById(transportationId);
            ActionTime timeRegistration = transportation.getTimeRegistration();
            if (timeRegistration == null) {
                timeRegistration = new ActionTime();
                transportation.setTimeRegistration(timeRegistration);
            }
            timeRegistration.setCreator(getWorker(req));
            timeRegistration.setTime(new Timestamp(System.currentTimeMillis()));
            dao.save(timeRegistration);
            dao.save(transportation);
            updateUtil.onSave(transportation);
            write(resp, SUCCESS_ANSWER);

            Weight weight = transportation.getWeight();

            if (weight == null || (weight.getBrutto() == 0 && weight.getTara() == 0)) {
                Notificator notificator = BotFactory.getNotificator();
                if (notificator != null) {
                    notificator.transportRegistration(transportation);
                }
            }

        }
    }
}
