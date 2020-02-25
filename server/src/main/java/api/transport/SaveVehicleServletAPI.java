package api.transport;

import api.ServletAPI;
import constants.Branches;
import constants.Constants;
import entity.transport.Trailer;
import entity.transport.Vehicle;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import utils.Parser;
import utils.U;
import utils.UpdateUtil;
import utils.answers.SuccessAnswer;

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
            if (body.containsKey(VEHICLE_ID)) {
                vehicle = dao.getObjectById(Vehicle.class, body.get(VEHICLE_ID));
                logger.info("Edit vehicle " + vehicle.getId() + "...");
            } else {
                vehicle = new Vehicle();
                logger.info("Create new vehicle...");
            }
            vehicle.setModel(String.valueOf(body.get(MODEL)).toUpperCase());
            logger.info("\t...Model: " + vehicle.getModel());

            vehicle.setNumber(Parser.prettyNumber(String.valueOf(body.get(NUMBER))));
            logger.info("\t...Number: " + vehicle.getNumber());
            String trailerNumber = String.valueOf(body.get(TRAILER));
            if (U.exist(trailerNumber)) {
                vehicle.setTrailerNumber(Parser.prettyNumber(trailerNumber));
                logger.info("\t...Trailer: " + vehicle.getTrailerNumber());
                Trailer trailer = vehicle.getTrailer();
                if (trailer == null){
                    trailer = new Trailer();
                }
                trailer.setNumber(vehicle.getTrailerNumber());
                dao.save(trailer);
                vehicle.setTrailer(trailer);
            }

            dao.save(vehicle);
            updateUtil.onSave(vehicle);
            JSONObject json = new SuccessAnswer(RESULT, vehicle.toJson()).toJson();
            write(resp, json.toJSONString());
            pool.put(json);
            body.clear();
        } else {
            write(resp, EMPTY_BODY);
        }
    }
}
