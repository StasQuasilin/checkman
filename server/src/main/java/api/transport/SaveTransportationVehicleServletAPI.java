package api.transport;

import api.ServletAPI;
import constants.Branches;
import constants.Constants;
import entity.documents.LoadPlan;
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
@WebServlet(Branches.API.SAVE_TRANSPORTATION_VEHICLE)
public class SaveTransportationVehicleServletAPI extends ServletAPI {

    private final TransportationComparator comparator = new TransportationComparator();
    final UpdateUtil updateUtil = new UpdateUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);

        if (body != null) {
            System.out.println(body);
            LoadPlan plan = dao.getLoadPlanById(body.get(Constants.TRANSPORTATION));
            Transportation transportation = plan.getTransportation();
            comparator.fix(transportation);
            long vehicleId = -1;

            if (body.containsKey(Constants.VEHICLE)){
                vehicleId = (long) body.get(Constants.VEHICLE);
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
            updateUtil.onSave(transportation);
            comparator.compare(transportation, getWorker(req));

            write(resp, SUCCESS_ANSWER);

            body.clear();
        } else {
            write(resp, EMPTY_BODY);
        }

    }
}
