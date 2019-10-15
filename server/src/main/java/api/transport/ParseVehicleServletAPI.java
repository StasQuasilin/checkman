package api.transport;

import api.ServletAPI;
import constants.Branches;
import constants.Constants;
import entity.documents.LoadPlan;
import entity.transport.Vehicle;
import org.json.simple.JSONObject;
import utils.Parser;
import utils.U;
import utils.UpdateUtil;
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
    private static final long serialVersionUID = 3416365884586446160L;
    private final UpdateUtil updateUtil = new UpdateUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            String key = String.valueOf(body.get(Constants.KEY));

            Vehicle vehicle = VehicleParser.parse(key);
            dao.save(vehicle);
            write(resp, SUCCESS_ANSWER);
            if (body.containsKey(Constants.TRANSPORTATION)){
                LoadPlan plan = dao.getLoadPlanById(body.get(Constants.TRANSPORTATION));
                plan.getTransportation().setVehicle(vehicle);
                dao.save(plan.getTransportation());
                updateUtil.onSave(plan.getTransportation());
            }
        }
    }
}
