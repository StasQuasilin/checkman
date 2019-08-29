package api.transport;

import api.ServletAPI;
import constants.Branches;
import constants.Constants;
import entity.log.comparators.TransportationComparator;
import entity.transport.Driver;
import entity.transport.Transportation;
import entity.transport.Vehicle;
import org.json.simple.JSONObject;
import utils.UpdateUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 21.03.2019.
 */
@WebServlet(Branches.API.SAVE_TRANSPORTATION_DRIVER)
public class SaveTransportationDriverServletAPI extends ServletAPI {

    private final TransportationComparator comparator = new TransportationComparator();
    final UpdateUtil updateUtil = new UpdateUtil();


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            Transportation transportation = dao.getTransportationById(body.get(Constants.TRANSPORTATION_ID));
            comparator.fix(transportation);
            long driverId = -1;
            if (body.containsKey(Constants.DRIVER_ID)){
                driverId = (long) body.get(Constants.DRIVER_ID);
            }
            if (driverId != -1) {
                Driver driver = dao.getDriverByID(driverId);
                if (driver.getVehicle() == null) {
                    Vehicle vehicle = transportation.getVehicle();
                    if(vehicle != null) {
                        driver.setVehicle(vehicle);
                        dao.save(driver);
                    }
                }
                transportation.setDriver(driver);
                if (transportation.getVehicle() == null) {
                    if (driver.getVehicle() != null) {
                        transportation.setVehicle(driver.getVehicle());
                    }
                }
            } else {
                transportation.setDriver(null);
            }

            dao.save(transportation);
            updateUtil.onSave(transportation);
            comparator.compare(transportation, getWorker(req));
            write(resp, SUCCESS_ANSWER);
            body.clear();
        } else {write(resp, emptyBody);}
    }
}
