package controllers.transport;

import constants.Branches;
import constants.Constants;
import controllers.IModal;
import entity.transport.Vehicle;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import utils.Parser;
import utils.PostUtil;
import utils.UpdateUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by szpt_user045 on 21.03.2019.
 */
@WebServlet(Branches.UI.EDIT_VEHICLE)
public class EditVehicle extends IModal {
    
    static final Logger log = Logger.getLogger(EditVehicle.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = PostUtil.parseBodyJson(req);
        log.info(body);
        Vehicle vehicle = null;

        if (body != null) {

            if (body.containsKey(Constants.ID)) {
                vehicle = dao.getVehicleById(body.get(Constants.ID));
            } else if (body.containsKey(Constants.KEY)){
                vehicle = new Vehicle();
                List<String> strings = Parser.parseVehicle(String.valueOf(body.get(Constants.KEY)));
                log.info("New vehicle...");
                if (strings.size() > 0) {
                    vehicle.setModel(strings.get(0));
                    log.info("\t...Model: " + vehicle.getModel());
                    if (strings.size() > 1) {
                        vehicle.setNumber((strings.get(1)));
                        log.info("\t...Number: " + vehicle.getNumber());
                        if (strings.size() > 2) {
                            vehicle.setTrailer((strings.get(2)));
                            log.info("\t...Trailer: " + vehicle.getTrailer());
                        }
                    }
                }
            }
            if (vehicle == null) {
                vehicle = new Vehicle();
                if (body.containsKey("model")){
                    vehicle.setModel(String.valueOf(body.get("model")));
                }
                if (body.containsKey("number")){
                    vehicle.setNumber(String.valueOf(body.get("number")));
                }
                if (body.containsKey("trailer")){
                    vehicle.setTrailer(String.valueOf(body.get("trailer")));
                }
            }
        } else {
            log.warn("Body parse error!");
        }
        req.setAttribute("vehicle", vehicle);
        req.setAttribute("findOrganisation", Branches.API.References.FIND_ORGANISATION);
        req.setAttribute("saveVehicleAPI", Branches.API.References.SAVE_VEHICLE);
        req.setAttribute("modalContent", "/pages/transport/vehicleInput.jsp");
        req.setAttribute("title", Constants.Titles.VEHICLE_INPUT);
        show(req, resp);
    }
}