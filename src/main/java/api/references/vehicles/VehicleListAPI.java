package api.references.vehicles;

import api.ServletAPI;
import constants.Branches;
import entity.transport.Vehicle;
import org.json.simple.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 02.03.2020.
 */
@WebServlet(Branches.API.References.VEHICLE_LIST)
public class VehicleListAPI extends ServletAPI {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONArray array = pool.getArray();
        for (Vehicle v : dao.getObjects(Vehicle.class)){
            array.add(v.toJson());
        }
        write(resp, array.toJSONString());
        pool.put(array);
    }
}
