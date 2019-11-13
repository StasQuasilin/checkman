package api.transport;

import api.ServletAPI;
import constants.Branches;
import constants.Constants;
import entity.documents.LoadPlan;
import entity.transport.Transportation2;
import entity.transport.Vehicle;
import org.json.simple.JSONObject;
import utils.*;
import utils.answers.SuccessAnswer;

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
            JSONObject object = parser.toJson(new SuccessAnswer("vehicle", parser.toJson(vehicle)));
            String s = object.toJSONString();
            write(resp, s);
            pool.put(object);
            if (body.containsKey(Constants.TRANSPORTATION)){
                Transportation2 plan = dao.getObjectById(Transportation2.class, body.get(Constants.TRANSPORTATION));
//                plan.setVehicle(vehicle);
                dao.save(plan);
//                updateUtil.onSave(plan.getTransportation());
            }
        }
    }
}
