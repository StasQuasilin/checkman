package controllers.deals;

import constants.Branches;
import constants.Constants;
import controllers.IModal;
import controllers.IServlet;
import entity.DealType;
import entity.Product;
import entity.documents.Deal;
import entity.documents.DocumentOrganisation;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@WebServlet(Branches.UI.DEAL_EDIT)
public class DealEdit extends IModal {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(req.getParameter(Constants.ID));
            req.setAttribute("deal", hibernator.get(Deal.class, "id", id));
            req.setAttribute("title", Constants.Languages.DEAL_EDIT);
        } catch (Exception ignored){
            req.setAttribute("title", Constants.Languages.DEAL_CREATE);
        }
        req.setAttribute("type", req.getParameter("type"));
        req.setAttribute("types", DealType.values());
        req.setAttribute("products", hibernator.query(Product.class, null));
        req.setAttribute("documentOrganisations", hibernator.query(DocumentOrganisation.class, "active", true));
        req.setAttribute("find_organisation", Branches.API.References.FIND_ORGANISATION);
        req.setAttribute("parse_organisation", Branches.API.References.PARSE_ORGANISATION);
        req.setAttribute("save_url", Branches.API.DEAL_SAVE);
        req.setAttribute("modalContent", "/pages/deals/dealEdit.jsp");
        show(req, resp);
    }
}
