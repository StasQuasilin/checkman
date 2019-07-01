package controllers.deals;

import constants.Branches;
import constants.Constants;
import controllers.IModal;
import entity.transport.TransportCustomer;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import utils.PostUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@WebServlet(Branches.UI.DEAL_SHOW)
public class DealShow extends IModal{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int dealId = -1;

        String parameterId = req.getParameter(Constants.ID);
        if (parameterId != null) {
            dealId = Integer.parseInt(parameterId);
        } else {
            JSONObject body = PostUtil.parseBodyJson(req);
            if (body != null && body.containsKey(Constants.ID)){
                dealId = Integer.parseInt(String.valueOf(body.remove(Constants.ID)));
            }
        }

        if (dealId != -1) {
            req.setAttribute("deal", dao.getDealById(dealId));
            req.setAttribute("title", Constants.Titles.DEAL_SHOW);
            req.setAttribute("update", Branches.API.PLAN_LIST);
            req.setAttribute("save", Branches.API.PLAN_LIST_SAVE);
            req.setAttribute("vehicleModal", Branches.UI.VEHICLE_MODAL);
            req.setAttribute("driverModal", Branches.UI.DRIVER_MODAL);
            req.setAttribute("findVehicleAPI", Branches.API.References.FIND_VEHICLE);
            req.setAttribute("findDriverAPI", Branches.API.References.FIND_DRIVER);
            req.setAttribute("editVehicle", Branches.UI.VEHICLE_MODAL);
            req.setAttribute("editDriver", Branches.UI.DRIVER_MODAL);
            req.setAttribute("modalContent", "/pages/deals/dealShow.jsp");
            req.setAttribute("customers", TransportCustomer.values());
            show(req, resp);
        }

    }
}
