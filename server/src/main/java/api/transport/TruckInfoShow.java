package api.transport;

import constants.Branches;
import controllers.IModal;
import entity.transport.TruckInfo;
import entity.transport.Vehicle;
import org.json.simple.JSONObject;
import utils.OpenDataBotAPI;
import utils.TruckInfoUtil;

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

    private TruckInfoUtil info = new TruckInfoUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            Vehicle vehicle = dao.getObjectById(Vehicle.class, body.get(ID));
            req.setAttribute(VEHICLES, info.getInfo(vehicle.getNumber()));
            if (vehicle.getTrailer() != null) {
                req.setAttribute(TRAILERS, info.getInfo(vehicle.getTrailer().getNumber()));
            }
            req.setAttribute(TITLE, _TITLE);
            req.setAttribute(MODAL_CONTENT, _CONTENT);
            show(req, resp);
        }
    }
}
