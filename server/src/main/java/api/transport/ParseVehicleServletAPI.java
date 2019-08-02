package api.transport;

import api.ServletAPI;
import constants.Branches;
import constants.Constants;
import entity.transport.Vehicle;
import org.json.simple.JSONObject;
import utils.Parser;
import utils.U;
import utils.VehicleParser;

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

            Vehicle vehicle = VehicleParser.parse(key);
            JSONObject ans = parser.toJson(vehicle);
            write(resp, ans.toJSONString());
            pool.put(ans);
        }
    }
}
