package api.transport;

import api.ServletAPI;
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
public class SaveTransportationVehicleServletAPI extends ServletAPI {

    private final TransportationComparator comparator = new TransportationComparator();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);

        if (body != null) {
            Transportation transportation = dao.getTransportationById(body.get(Constants.TRANSPORTATION_ID));
            comparator.fix(transportation);
            long vehicleId = -1;
            if (body.containsKey(Constants.VEHICLE_ID)){
                vehicleId = (long) body.get(Constants.VEHICLE_ID);
            }

            if (vehicleId != -1){
                Vehicle vehicle = dao.getVehicleById(vehicleId);
                Driver driver = transportation.getDriver();
                if (driver != null) {
                    if (driver.getVehicle() == null){
                        driver.setVehicle(vehicle);
                        dao.save(driver);
                    }
                }
                transportation.setVehicle(vehicle);
            } else {
                transportation.setVehicle(null);
            }

            dao.saveTransportation(transportation);
            comparator.compare(transportation, getWorker(req));
            write(resp, answer);

            body.clear();
        } else {
            write(resp, emptyBody);
        }

    }
}