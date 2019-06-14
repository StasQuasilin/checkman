package api.transport;

import api.API;
import constants.Branches;
import constants.Constants;
import entity.log.comparators.TransportationComparator;
import entity.transport.Driver;
import entity.transport.Transportation;
import entity.transport.Vehicle;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 21.03.2019.
 */
@WebServlet(Branches.API.SAVE_TRANSPORTATION_DRIVER)
public class SaveTransportationDriverAPI extends API {

    private final TransportationComparator comparator = new TransportationComparator();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            long transportationId = (long) body.get(Constants.TRANSPORTATION_ID);
            long driverId = (long) body.get(Constants.DRIVER_ID);
            Transportation transportation = hibernator.get(Transportation.class, "id", transportationId);
            comparator.fix(transportation);

            Driver driver = hibernator.get(Driver.class, "id", driverId);
            if (driver.getVehicle() == null) {
                Vehicle vehicle = transportation.getVehicle();
                if(vehicle != null) {
                    driver.setVehicle(vehicle);
                    hibernator.save(driver);
                }
            }
            transportation.setDriver(driver);
            if (transportation.getVehicle() == null) {
                if (driver.getVehicle() != null) {
                    transportation.setVehicle(driver.getVehicle());
                }
            }
            hibernator.save(transportation);
            comparator.compare(transportation, getWorker(req));
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
