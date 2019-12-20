package api.transport;

import constants.Branches;
import controllers.IModal;
import entity.transport.TruckInfo;
import entity.transport.Vehicle;
import org.json.simple.JSONObject;
import utils.OpenDataBotAPI;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 19.12.2019.
 */
@WebServlet(Branches.UI.TRUCK_INFO)
public class TruckInfoShow extends IModal {
    private static final String _TITLE = "title.vehicle.info";
    private static final String _CONTENT = "/pages/transport/transportInfo.jsp";

    private final OpenDataBotAPI openData = new OpenDataBotAPI();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            Vehicle vehicle = dao.getObjectById(Vehicle.class, body.get(ID));
            if (vehicle != null){
                TruckInfo truckInfo = dao.getTruckInfo(vehicle);
                if (truckInfo == null){
                    truckInfo = new TruckInfo();
                    truckInfo.setTruck(vehicle);

                    StringBuilder builder = new StringBuilder();
                    for (char c : vehicle.getNumber().toCharArray()){
                        if (Character.isDigit(c) || Character.isLetter(c)){
                            builder.append(c);
                        }
                    }
                    openData.InfoRequest(builder.toString(), truckInfo);
                    if (truckInfo.getVin() != null || truckInfo.getModel() != null) {
                        dao.save(truckInfo);
                    }

                }

                req.setAttribute(INFO, truckInfo);
                req.setAttribute(TITLE, _TITLE);
                req.setAttribute(MODAL_CONTENT, _CONTENT);
                show(req, resp);
            }
        }
    }
}
