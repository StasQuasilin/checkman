package api.transport;

import api.IAPI;
import constants.Branches;
import constants.Constants;
import entity.answers.IAnswer;
import entity.transport.Driver;
import entity.transport.Transportation;
import entity.transport.Vehicle;
import jdk.internal.org.objectweb.asm.tree.FieldInsnNode;
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
@WebServlet(Branches.API.SAVE_TRANSPORTATION_VEHICLE)
public class SaveTransportationVehicleAPI extends IAPI {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);

        if (body != null) {
            long transportationId = (long) body.get(Constants.TRANSPORTATION_ID);
            long vehicleId = (long) body.get(Constants.VEHICLE_ID);
            Transportation transportation = hibernator.get(Transportation.class, "id", transportationId);
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
