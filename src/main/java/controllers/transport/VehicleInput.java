package controllers.transport;

import constants.Branches;
import constants.Constants;
import controllers.IModal;
import entity.transport.Vehicle;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import utils.Parser;
import utils.PostUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by szpt_user045 on 21.03.2019.
 */
@WebServlet(Branches.UI.VEHICLE_MODAL)
public class VehicleInput extends IModal {
    
    static final Logger log = Logger.getLogger(VehicleInput.class); 
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = PostUtil.parseBodyJson(req);
        Vehicle vehicle = null;
        long transportationId = -1;
        if (body != null) {
            if (body.containsKey(Constants.TRANSPORTATION_ID)) {
                transportationId = (long) body.get(Constants.TRANSPORTATION_ID);
                log.info("Transportation: " + transportationId);
            }

            if (body.containsKey(Constants.VEHICLE_ID)) {
                long vehicleId = (long) body.get(Constants.VEHICLE_ID);
                vehicle = hibernator.get(Vehicle.class, "id", vehicleId);
                log.info("Vehicle: " + vehicleId);
            } else {
                vehicle = new Vehicle();
                List<String> strings = Parser.parseVehicle(String.valueOf(body.get(Constants.KEY)));
                log.info("New vehicle...");
                if (strings.size() > 0) {
                    vehicle.setModel(strings.get(0));
                    log.info("\t...Model: " + vehicle.getModel());
                }
                if (strings.size() > 1) {
                    vehicle.setNumber(strings.get(1));
                    log.info("\t...Number: " + vehicle.getNumber());
                }
                if (strings.size() > 2) {
                    vehicle.setTrailer(strings.get(2));
                    log.info("\t...Trailer: " + vehicle.getTrailer());
                }
            }
        } else {
            log.warn("Body parse error!");
        }
        req.setAttribute("vehicle", vehicle);
        req.setAttribute("findOrganisationAPI", Branches.API.References.FIND_ORGANISATION);
        req.setAttribute("saveVehicleAPI", Branches.API.References.SAVE_VEHICLE);
        req.setAttribute("transportation", transportationId);
        req.setAttribute("modalContent", "/pages/transport/vehicleInput.jsp");
        req.setAttribute("title", Constants.Titles.VEHICLE_INPUT);
        show(req, resp);
    }
}
