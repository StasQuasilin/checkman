package controllers.deals;

import constants.Branches;
import constants.Constants;
import constants.Titles;
import controllers.IModal;
import entity.deal.Contract;
import entity.transport.TransportCustomer;
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
        int contractId = -1;

        String parameterId = req.getParameter(ID);
        if (parameterId != null) {
            contractId = Integer.parseInt(parameterId);
        } else {
            JSONObject body = parseBody(req);
            if (body != null && body.containsKey(ID)){
                contractId = Integer.parseInt(String.valueOf(body.remove(Constants.ID)));
            }
        }

        if (contractId != -1) {
            req.setAttribute(CONTRACT, dao.getObjectById(Contract.class, contractId));
            req.setAttribute(TITLE, Titles.DEAL_SHOW);
            req.setAttribute(SAVE, Branches.API.PLAN_LIST_SAVE);
            req.setAttribute("remove", Branches.API.REMOVE_PLAN);
            req.setAttribute("findVehicle", Branches.API.References.FIND_VEHICLE);
            req.setAttribute("parseVehicle", Branches.API.PARSE_VEHICLE);
            req.setAttribute("findDriver", Branches.API.References.FIND_DRIVER);
            req.setAttribute("parseDriver", Branches.API.PARSE_PERSON);
            req.setAttribute("editVehicle", Branches.UI.EDIT_VEHICLE);
            req.setAttribute("editDriver", Branches.UI.EDIT_DRIVER);
            req.setAttribute(MODAL_CONTENT, "/pages/deals/dealShow.jsp");
            req.setAttribute(CUSTOMERS, TransportCustomer.values());
            show(req, resp);
        }

    }
}
