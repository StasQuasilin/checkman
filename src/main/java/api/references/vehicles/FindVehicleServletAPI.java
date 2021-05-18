package api.references.vehicles;

import api.ServletAPI;
import constants.Branches;
import entity.transport.Vehicle;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 12.03.2019.
 */
@WebServlet(Branches.API.References.FIND_VEHICLE)
public class FindVehicleServletAPI extends ServletAPI {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JSONArray array = new JSONArray();
        JSONObject body = parseBody(req);
        if (body != null) {
            String key = String.valueOf(body.get(KEY));
            for (Vehicle v : dao.findVehicle(Vehicle.class, key)){
                array.add(v.toJson());
            }

        }
        write(resp, array.toJSONString());
        pool.put(array);
    }
}
