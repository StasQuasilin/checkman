package api.transport;

import api.IAPI;
import constants.Branches;
import constants.Constants;
import entity.organisations.Organisation;
import entity.transport.Transportation;
import entity.transport.Vehicle;
import org.apache.log4j.Logger;
import org.hibernate.annotations.common.util.impl.Log;
import utils.JsonParser;
import utils.PostUtil;
import utils.answers.SuccessAnswer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@WebServlet(Branches.API.References.SAVE_VEHICLE)
public class SaveVehicleAPI extends IAPI{

    final Logger logger = Logger.getLogger(SaveVehicleAPI.class);
    final String answer = JsonParser.toJson(new SuccessAnswer()).toJSONString();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HashMap<String, String> body = PostUtil.parseBody(req);
        Vehicle vehicle;
        try {
            int id = Integer.parseInt(body.get(Constants.ID));
            vehicle = hibernator.get(Vehicle.class, "id", id);
            if (vehicle == null){
                throw new Exception();
            }
            logger.info("Edit vehicle " + vehicle.getId() + "...");
        } catch (Exception ignored){
            vehicle = new Vehicle();
            logger.info("Create new vehicle...");
        }
        vehicle.setModel(body.get(Constants.Vehicle.MODEL));
        logger.info("\t...Model: " + vehicle.getModel());

        vehicle.setNumber(body.get(Constants.Vehicle.NUMBER));
        logger.info("\t...Number: " + vehicle.getNumber());

        vehicle.setTrailer(body.get(Constants.Vehicle.TRAILER));
        logger.info("\t...Trailer: " + vehicle.getTrailer());
        try {
            int id = Integer.parseInt(body.get(Constants.Vehicle.TRANSPORTER_ID));
            Organisation organisation = hibernator.get(Organisation.class, "id", id);
            vehicle.setTransporter(organisation);
            logger.info("\t...Transporter: \'" + vehicle.getTransporter().getValue() + "\'");
        } catch (Exception ignored){}

        hibernator.save(vehicle);

        try {
            int id = Integer.parseInt(body.get(Constants.TRANSPORTATION_ID));
            Transportation transportation = hibernator.get(Transportation.class, "id", id);
            transportation.setVehicle(vehicle);
            hibernator.save(transportation);
            logger.info("Put in transportation " + transportation.getId());
        } catch (Exception ignored){}

        write(resp, answer);
        body.clear();
    }
}
