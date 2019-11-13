package api.transport;

import api.ServletAPI;
import constants.Branches;
import constants.Constants;
import entity.transport.Vehicle;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import utils.Parser;
import utils.UpdateUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@WebServlet(Branches.API.References.SAVE_VEHICLE)
public class SaveVehicleServletAPI extends ServletAPI {

    final Logger logger = Logger.getLogger(SaveVehicleServletAPI.class);
    final UpdateUtil updateUtil = new UpdateUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        Vehicle vehicle;

        if (body != null) {
            logger.info(body);
            if (body.containsKey(Constants.VEHICLE_ID)) {
                vehicle = dao.getVehicleById(body.get(Constants.VEHICLE_ID));
                logger.info("Edit vehicle " + vehicle.getId() + "...");
            } else {
                vehicle = new Vehicle();
                logger.info("Create new vehicle...");
            }
            vehicle.setModel(String.valueOf(body.get(Constants.Vehicle.MODEL)).toUpperCase());
            logger.info("\t...Model: " + vehicle.getModel());

            vehicle.setNumber(Parser.prettyNumber(String.valueOf(body.get(Constants.Vehicle.NUMBER))));
            logger.info("\t...VehicleNumber: " + vehicle.getNumber());

            vehicle.setTrailer(Parser.prettyNumber(String.valueOf(body.get(Constants.Vehicle.TRAILER))));
            logger.info("\t...Trailer: " + vehicle.getTrailer());

            dao.save(vehicle);
//            updateUtil.onSave(vehicle);
            JSONObject jsonObject = parser.toJson(vehicle);
            write(resp, jsonObject.toJSONString());
            pool.put(jsonObject);
            body.clear();
        } else {
            write(resp, EMPTY_BODY);
        }
    }
}
