package api.transport;

import api.ServletAPI;
import constants.Branches;
import constants.Constants;
import entity.transport.Vehicle;
import org.json.simple.JSONObject;
import utils.Parser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by szpt_user045 on 12.07.2019.
 */
@WebServlet(Branches.API.PARSE_VEHICLE)
public class ParseVehicleServletAPI extends ServletAPI {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            String key = String.valueOf(body.get(Constants.KEY));
            List<String> vehicleData = Parser.parseVehicle(key);
            Vehicle vehicle = new Vehicle();
            if (vehicleData.size() > 0) {
                vehicle.setModel(vehicleData.get(0));
            }
            if (vehicleData.size() > 1) {
                vehicle.setNumber(vehicleData.get(1));
            }
            if (vehicleData.size() > 2){
                vehicle.setTrailer(vehicleData.get(2));
            }

            if (vehicleData.size() > 1) {
                List<Vehicle> vehicles = dao.findVehicle(vehicleData.get(1));
                if (vehicles.size() == 1){
                    vehicle = vehicles.get(0);
                } else if (vehicleData.size() > 2){
                    vehicles = dao.findVehicle(vehicleData.get(2));
                    if (vehicles.size() == 1){
                        vehicle = vehicles.get(0);
                    }
                }
            }
            JSONObject ans = parser.toJson(vehicle);
            write(resp, ans.toJSONString());
            pool.put(ans);
        }
    }
}
