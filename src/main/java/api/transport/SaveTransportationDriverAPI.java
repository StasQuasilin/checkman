package api.transport;

import api.IAPI;
import constants.Branches;
import constants.Constants;
import entity.answers.IAnswer;
import entity.transport.Driver;
import entity.transport.Transportation;
import entity.transport.Vehicle;
import org.json.simple.JSONObject;
import utils.JsonParser;
import utils.PostUtil;
import utils.answers.SuccessAnswer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by szpt_user045 on 21.03.2019.
 */
@WebServlet(Branches.API.SAVE_TRANSPORTATION_DRIVER)
public class SaveTransportationDriverAPI extends IAPI {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            long transportationId = (long) body.get(Constants.TRANSPORTATION_ID);
            long driverId = (long) body.get(Constants.DRIVER_ID);
            Transportation transportation = hibernator.get(Transportation.class, "id", transportationId);
            Driver driver = hibernator.get(Driver.class, "id", driverId);
            if (driver.getVehicle() == null) {
                Vehicle vehicle = transportation.getVehicle();
                if(vehicle != null) {
                    driver.setVehicle(vehicle);
                    hibernator.save(driver);
                }
            }
            transportation.setDriver(driver);
            hibernator.save(transportation);
            write(resp, answer);
            body.clear();
        } else {write(resp, emptyBody);}
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            Transportation transportation = hibernator.get(Transportation.class, "id", body.get(Constants.TRANSPORTATION_ID));
            transportation.setDriver(null);
            hibernator.save(transportation);
        } else {
            write(resp, emptyBody);
        }
    }
}
