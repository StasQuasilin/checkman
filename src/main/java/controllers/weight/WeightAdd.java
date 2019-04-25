package controllers.weight;

import constants.Branches;
import constants.Constants;
import controllers.IModal;
import entity.DealType;
import entity.Product;
import entity.documents.DocumentOrganisation;
import entity.documents.LoadPlan;
import entity.transport.TransportCustomer;
import entity.weight.WeightUnit;
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
            req.setAttribute("plan", hibernator.get(LoadPlan.class, "id", id));
            req.setAttribute("title", "title.weight.edit");
        } else if (copy != -1) {
            LoadPlan plan = hibernator.get(LoadPlan.class, "id", copy);
            plan.setId(-1);
            req.setAttribute("plan", plan);
            req.setAttribute("title", "title.weight.copy");
        } else {
            req.setAttribute("title", "title.weight.add");
        }

        req.setAttribute("modalContent", "/pages/weight/weightAdd.jsp");
        req.setAttribute("findOrganisations", Branches.API.References.FIND_ORGANISATION);
        req.setAttribute("parseOrganisation", Branches.API.References.PARSE_ORGANISATION);
        req.setAttribute("findDeals", Branches.API.FIND_DEALS);
        req.setAttribute("findVehicle", Branches.API.References.FIND_VEHICLE);
        req.setAttribute("parseVehicle", Branches.UI.VEHICLE_MODAL);
        req.setAttribute("findDriver", Branches.API.References.FIND_DRIVER);
        req.setAttribute("parseDriver", Branches.UI.DRIVER_MODAL);
        req.setAttribute("save", Branches.API.PLAN_LIST_ADD);
        req.setAttribute("products", hibernator.query(Product.class, null));
        req.setAttribute("units", hibernator.query(WeightUnit.class, null));
        req.setAttribute("documentOrganisations", hibernator.query(DocumentOrganisation.class, "active", true));
        req.setAttribute("types", DealType.values());
        req.setAttribute("customers", TransportCustomer.values());
        show(req, resp);
    }
}
