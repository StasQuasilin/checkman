package controllers.weight;

import constants.Branches;
import controllers.IModal;
import entity.Product;
import entity.weight.WeightUnit;

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
        req.setAttribute("title", "title.weight.add");
        req.setAttribute("modalContent", "/pages/weight/weightAdd.jsp");
        req.setAttribute("findOrganisations", Branches.API.References.FIND_ORGANISATION);
        req.setAttribute("findVehicle", Branches.API.References.FIND_VEHICLE);
        req.setAttribute("parseVehicle", Branches.UI.VEHICLE_MODAL);
        req.setAttribute("findDriver", Branches.API.References.FIND_DRIVER);
        req.setAttribute("parseDriver", Branches.UI.DRIVER_MODAL);
        req.setAttribute("parseOrganisation", Branches.API.References.PARSE_ORGANISATION);
        req.setAttribute("findDeals", Branches.API.FIND_DEALS);
        req.setAttribute("save", Branches.API.PLAN_LIST_SAVE);
        req.setAttribute("products", hibernator.query(Product.class, null));
        req.setAttribute("units", hibernator.query(WeightUnit.class, null));
        show(req, resp);
    }
}
