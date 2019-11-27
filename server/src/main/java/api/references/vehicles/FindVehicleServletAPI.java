package api.references.vehicles;

import api.ServletAPI;
import constants.Branches;
import entity.transport.Vehicle;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

/**
 * Created by szpt_user045 on 12.03.2019.
 */
@WebServlet(Branches.API.References.FIND_VEHICLE)
public class FindVehicleServletAPI extends ServletAPI {

    final Logger log = Logger.getLogger(FindVehicleServletAPI.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JSONArray array = new JSONArray();
        JSONObject body = parseBody(req);
        if (body != null) {
            Object key = body.get(KEY);
            array.addAll(dao.findVehicle(Vehicle.class, key).stream().map(Vehicle::toJson).collect(Collectors.toCollection(JSONArray::new)));
        }
        write(resp, array.toJSONString());
        array.clear();
    }
}
