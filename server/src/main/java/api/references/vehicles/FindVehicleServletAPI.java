package api.references.vehicles;

import api.ServletAPI;
import constants.Branches;
import entity.transport.TruckInfo;
import entity.transport.Vehicle;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.OpenDataBotAPI;
import utils.TruckInfoUtil;
import utils.U;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Created by szpt_user045 on 12.03.2019.
 */
@WebServlet(Branches.API.References.FIND_VEHICLE)
public class FindVehicleServletAPI extends ServletAPI {

    final Logger log = Logger.getLogger(FindVehicleServletAPI.class);
    private final TruckInfoUtil infoUtil = new TruckInfoUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JSONArray array = new JSONArray();
        JSONObject body = parseBody(req);
        if (body != null) {
            Object key = body.get(KEY);
            for (Vehicle v : dao.findVehicle(Vehicle.class, key)){
                if (!U.exist(v.getModel())){
                    ArrayList<TruckInfo> info = infoUtil.getInfo(v.getNumber());
                    if (info.size() > 0){
                        v.setModel(info.get(0).getBrand());
                        dao.save(v);
                    }
                }
                array.add(v.toJson());
            }

        }
        write(resp, array.toJSONString());
        pool.put(array);
    }
}
