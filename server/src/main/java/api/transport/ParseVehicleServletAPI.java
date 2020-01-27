package api.transport;

import api.ServletAPI;
import constants.Branches;
import constants.Constants;
import entity.documents.LoadPlan;
import entity.transport.TransportUtil;
import entity.transport.TruckInfo;
import entity.transport.Vehicle;
import org.json.simple.JSONObject;
import utils.*;
import utils.answers.SuccessAnswer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by szpt_user045 on 12.07.2019.
 */
@WebServlet(Branches.API.PARSE_VEHICLE)
public class ParseVehicleServletAPI extends ServletAPI {
    private final UpdateUtil updateUtil = new UpdateUtil();
    private final TruckInfoUtil infoUtil = new TruckInfoUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            String key = String.valueOf(body.get(Constants.KEY));

            Vehicle vehicle = VehicleParser.parse(key);
            if (vehicle.getTrailer() != null){
                dao.save(vehicle.getTrailer());
            }

            dao.save(vehicle);
            JSONObject json = vehicle.toJson();
            SuccessAnswer answer = new SuccessAnswer(VEHICLE, json);
            answer.add(RESULT, json);

            JSONObject object = answer.toJson();
            write(resp, object.toJSONString());
            pool.put(object);
            if (body.containsKey(Constants.TRANSPORTATION)){
                LoadPlan plan = dao.getLoadPlanById(body.get(Constants.TRANSPORTATION));
                TransportUtil.setVehicle(plan.getTransportation(), vehicle);
                dao.save(plan.getTransportation());
                updateUtil.onSave(plan.getTransportation());
            }

            if (!U.exist(vehicle.getModel())){
                ArrayList<TruckInfo> infos = infoUtil.getInfo(vehicle.getNumber());
                if (infos.size() == 1){
                    TruckInfo info = infos.get(0);
                    if (U.exist(info.getBrand())){
                        vehicle.setModel(info.getBrand());
                        dao.save(vehicle);
                        updateUtil.onSave(vehicle);
                    }
                }
            }
        }
    }
}
