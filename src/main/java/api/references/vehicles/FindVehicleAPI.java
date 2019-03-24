package api.references.vehicles;

import api.IAPI;
import constants.Branches;
import constants.Constants;
import entity.transport.Vehicle;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.JsonParser;
import utils.PostUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * Created by szpt_user045 on 12.03.2019.
 */
@WebServlet(Branches.API.References.FIND_VEHICLE)
public class FindVehicleAPI extends IAPI{

    final Logger log = Logger.getLogger(FindVehicleAPI.class);
    final JSONArray array = new JSONArray();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = PostUtil.parseBodyJson(req);
        String key = (String) body.get(Constants.KEY);

        HashMap<Integer, Vehicle> result = new HashMap<>();
        find("model", key, result);
        find("number", key, result);
        find("trailer", key, result);

        array.addAll(result.values().stream().map(JsonParser::toJson).collect(Collectors.toCollection(JSONArray::new)));
        write(resp, array.toJSONString());

        body.clear();
        result.clear();
        array.clear();
    }

    synchronized void find(String key, String value, HashMap<Integer, Vehicle> result){
        for (Vehicle vehicle : hibernator.find(Vehicle.class, key, value)){
            result.put(vehicle.getId(), vehicle);
        }
    }
}
