package controllers.deals;

import constants.Branches;
import constants.Constants;
import controllers.IModal;
import entity.documents.Deal;
import entity.documents.LoadPlan;
import entity.transport.TransportCustomer;

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
        int dealId = Integer.parseInt(req.getParameter(Constants.ID));
        req.setAttribute("deal", hibernator.get(Deal.class, "id", dealId));
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
