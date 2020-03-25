package controllers.transport;

import constants.Branches;
import constants.Constants;
import constants.Titles;
import controllers.IModal;
import entity.transport.Trailer;
import entity.transport.Transportation;
import entity.transport.TruckInfo;
import entity.transport.Vehicle;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import utils.Parser;
import utils.PostUtil;
import utils.TruckInfoUtil;
import utils.U;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by szpt_user045 on 21.03.2019.
 */
@WebServlet(Branches.UI.EDIT_VEHICLE)
public class EditVehicle extends IModal {
    
    static final Logger log = Logger.getLogger(EditVehicle.class);
    TruckInfoUtil infoUtil = new TruckInfoUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        log.info(body);

        if (body != null) {
            if (body.containsKey(ID)) {
                Vehicle vehicle = dao.getObjectById(Vehicle.class, body.get(ID));
                req.setAttribute(VEHICLE, vehicle);
                if (U.exist(vehicle.getNumber())) {
                    ArrayList<TruckInfo> info = infoUtil.getInfo(vehicle.getNumber());
                    if (info.size() > 0 && !U.exist(vehicle.getModel())) {
                        vehicle.setModel(info.get(0).getBrand());
                        dao.save(vehicle);
                    }
                    req.setAttribute(INFO, info);
                }
            }
        }

        req.setAttribute("findOrganisation", Branches.API.References.FIND_ORGANISATION);
        req.setAttribute("saveVehicleAPI", Branches.API.References.SAVE_VEHICLE);
        req.setAttribute(DELETE, Branches.API.DELETE_VEHICLE);
        req.setAttribute(MODAL_CONTENT, "/pages/transport/vehicleInput.jsp");
        req.setAttribute(TITLE, Titles.VEHICLE_INPUT);
        show(req, resp);
    }
}
