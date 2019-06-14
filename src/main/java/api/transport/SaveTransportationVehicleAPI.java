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
@WebServlet(Branches.API.SAVE_TRANSPORTATION_VEHICLE)
public class SaveTransportationVehicleAPI extends API {

    private final TransportationComparator comparator = new TransportationComparator();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);

        if (body != null) {
            long transportationId = (long) body.get(Constants.TRANSPORTATION_ID);
            long vehicleId = (long) body.get(Constants.VEHICLE_ID);
            Transportation transportation = hibernator.get(Transportation.class, "id", transportationId);
            comparator.fix(transportation);
            Vehicle vehicle = hibernator.get(Vehicle.class, "id", vehicleId);
            Driver driver = transportation.getDriver();
            if (driver != null) {
                if (driver.getVehicle() == null){
                    driver.setVehicle(vehicle);
                    hibernator.save(driver);
                }
            }
            transportation.setVehicle(vehicle);
            hibernator.save(transportation);
            comparator.compare(transportation, getWorker(req));
            write(resp, answer);

            body.clear();
        } else {
            write(resp, emptyBody);
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            Transportation transportation = hibernator.get(Transportation.class, "id", body.get(Constants.TRANSPORTATION_ID));
            transportation.setVehicle(null);
            hibernator.save(transportation);
        } else {
            write(resp, emptyBody);
        }
    }
}
