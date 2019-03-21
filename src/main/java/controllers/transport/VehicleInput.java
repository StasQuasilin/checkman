package controllers.transport;

import constants.Branches;
import constants.Constants;
import controllers.IModal;
import entity.transport.Vehicle;
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
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HashMap<String, String> body = PostUtil.parseBody(req);
        Vehicle vehicle;
        try {
            int vehicleId = Integer.parseInt(body.get(Constants.VEHICLE_ID));
            vehicle = hibernator.get(Vehicle.class, "id", vehicleId);
        } catch (Exception ignored){
            vehicle = new Vehicle();
        }

        List<String> strings = Parser.parseVehicle(body.get(Constants.KEY));
        if (strings.size() > 0){
            vehicle.setModel(strings.get(0));
        }
        if(strings.size() > 1){
            vehicle.setNumber(strings.get(1));
        }
        if (strings.size() > 2){
            vehicle.setTrailer(strings.get(2));
        }
        req.setAttribute("vehicle", vehicle);
        req.setAttribute("findOrganisationAPI", Branches.API.References.FIND_ORGANISATION);
        req.setAttribute("saveVehicleAPI", Branches.API.References.SAVE_VEHICLE);
        req.setAttribute("transportation", body.get(Constants.TRANSPORTATION_ID));
        req.setAttribute("modalContent", "/pages/transport/vehicleInput.jsp");
        req.setAttribute("title", Constants.Titles.VEHICLE_INPUT);
        show(req, resp);
    }
}
