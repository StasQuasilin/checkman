package api.transport;

import api.API;
import constants.Branches;
import constants.Constants;
import entity.log.comparators.TransportationComparator;
import entity.organisations.Organisation;
import entity.transport.Transportation;
import entity.transport.Vehicle;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import utils.JsonParser;
import utils.Parser;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@WebServlet(Branches.API.References.SAVE_VEHICLE)
public class SaveVehicleAPI extends API {

    final Logger logger = Logger.getLogger(SaveVehicleAPI.class);
    final TransportationComparator comparator = new TransportationComparator();


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        Vehicle vehicle;

        if (body != null) {
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
            logger.info("\t...Number: " + vehicle.getNumber());

            vehicle.setTrailer(Parser.prettyNumber(String.valueOf(body.get(Constants.Vehicle.TRAILER))));
            logger.info("\t...Trailer: " + vehicle.getTrailer());

            if (body.containsKey(Constants.Vehicle.TRANSPORTER_ID)) {
                Organisation organisation = dao.getOrganisationById(body.get(Constants.Vehicle.TRANSPORTER_ID));
                vehicle.setTransporter(organisation);
                logger.info("\t...Transporter: \'" + vehicle.getTransporter().getValue() + "\'");
            }

            dao.save(vehicle);
            Object transportationId = null;

            if (body.containsKey(Constants.TRANSPORTATION_ID)) {
                transportationId = body.get(Constants.TRANSPORTATION_ID);
            }
            if (transportationId != null) {
                logger.info("Put in transportation " + transportationId.toString());
                Transportation transportation = dao.getTransportationById(transportationId);
                if (transportation != null) {
                    comparator.fix(transportation);
                    transportation.setVehicle(vehicle);
                    dao.saveTransportation(transportation);
                    comparator.compare(transportation, getWorker(req));
                }
            }

            write(resp, JsonParser.toJson(vehicle).toJSONString());
            body.clear();
        } else {
            write(resp, emptyBody);
        }
    }
}
