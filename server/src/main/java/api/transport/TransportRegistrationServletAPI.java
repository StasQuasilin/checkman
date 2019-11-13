package api.transport;

import api.ServletAPI;
import bot.BotFactory;
import bot.Notificator;
import constants.Branches;
import constants.Constants;
import entity.transport.ActionTime;
import entity.transport.Transportation2;
import entity.transport.TransportationProduct;
import entity.weight.Weight;
import org.json.simple.JSONObject;
import utils.UpdateUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
            Transportation2 transportation = dao.getObjectById(Transportation2.class, transportationId);
            ActionTime timeRegistration = transportation.getRegistered();
            if (timeRegistration == null) {
                timeRegistration = new ActionTime(getWorker(req));
                transportation.setRegistered(timeRegistration);
            }
            dao.save(timeRegistration);
            dao.save(transportation);
            updateUtil.onSave(transportation);
            write(resp, SUCCESS_ANSWER);

            boolean notify = true;
            for (TransportationProduct product : transportation.getProducts()){
                Weight weight = product.getWeight();
                if (weight != null && (weight.getBrutto() > 0 || weight.getTara() > 0)){
                    notify = false;
                }
            }
            if (notify) {
                Notificator notificator = BotFactory.getNotificator();
                if (notificator != null) {
                    notificator.transportAction(transportation);
                }
            }

        }
    }
}
