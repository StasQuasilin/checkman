package controllers.weight;

import constants.Branches;
import constants.Constants;
import controllers.IModal;
import entity.DealType;
import entity.Role;
import entity.documents.LoadPlan;
import entity.transport.TransportCustomer;
import org.json.simple.JSONObject;
import utils.PostUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 18.04.2019.
 */
@WebServlet(Branches.UI.WEIGHT_ADD)
public class WeightAdd extends IModal {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = PostUtil.parseBodyJson(req);
        long id = -1;
        long copy = -1;
        if (body != null) {
            if (body.containsKey(Constants.ID)) {
                id = Long.parseLong(String.valueOf(body.get(Constants.ID)));
            } else if (body.containsKey(Constants.COPY)) {
                copy = Long.parseLong(String.valueOf(body.get(Constants.COPY)));
            }
        }
        if (id != -1) {
            req.setAttribute(PLAN, dao.getLoadPlanById(id));
            req.setAttribute(TITLE, "title.weight.edit");
        } else if (copy != -1) {
            LoadPlan plan = dao.getLoadPlanById(copy);
            plan.setId(-1);
            req.setAttribute(PLAN, plan);
            req.setAttribute(TITLE, "title.weight.copy");
        } else {
            req.setAttribute(TITLE, "title.weight.add");
        }
        req.setAttribute("managers", dao.getWorkersByRole(Role.manager));
        req.setAttribute(MODAL_CONTENT, "/pages/weight/weightAdd.jsp");
        req.setAttribute("findDeals", Branches.API.FIND_DEALS);

        req.setAttribute("findOrganisations", Branches.API.References.FIND_ORGANISATION);
        req.setAttribute("parseOrganisation", Branches.API.References.PARSE_ORGANISATION);
        req.setAttribute("editOrganisation", Branches.UI.References.ORGANISATION_EDIT);

        req.setAttribute("findVehicle", Branches.API.References.FIND_VEHICLE);
        req.setAttribute("parseVehicle", Branches.API.PARSE_VEHICLE);
        req.setAttribute("editVehicle", Branches.UI.EDIT_VEHICLE);

        req.setAttribute("findDriver", Branches.API.References.FIND_DRIVER);
        req.setAttribute("parseDriver", Branches.API.PARSE_PERSON);
        req.setAttribute("editDriver", Branches.UI.EDIT_DRIVER);

        req.setAttribute(SAVE, Branches.API.PLAN_LIST_ADD);
        req.setAttribute(PRODUCTS, dao.getProductList());
        req.setAttribute(UNITS, dao.getUnitsList());
        req.setAttribute(SHIPPERS, dao.getShipperList());
        req.setAttribute(TYPES, DealType.values());
        req.setAttribute(CUSTOMERS, TransportCustomer.values());
        show(req, resp);
    }
}
