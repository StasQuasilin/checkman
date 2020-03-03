package api.references.vehicles;

import api.ServletAPI;
import constants.Branches;
import entity.transport.Vehicle;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 02.03.2020.
 */
@WebServlet(Branches.API.DELETE_VEHICLE)
public class DeleteVehicleAPI extends ServletAPI{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            Vehicle vehicle = dao.getObjectById(Vehicle.class, body.get(ID));
            if (vehicle != null){
                dao.remove(vehicle);
            }
            write(resp, SUCCESS_ANSWER);
        }
    }
}
