package controllers.weight;

import constants.Branches;
import controllers.IModal;
import entity.DealType;
import entity.documents.Deal;
import entity.organisations.Organisation;
import entity.products.ProductAction;
import entity.weight.Unit;
import org.json.simple.JSONObject;
import utils.hibernate.dao.DealDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

@WebServlet(Branches.UI.Transportation.DEAL_EDIT)
public class SimpleDealEdit extends IModal {

    private static final String _TITLE = "deal.edit";
    private static final String _CONTENT = "/pages/weight/dealEdit.jsp";
    private DealDAO dealDAO = new DealDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JSONObject body = parseBody(req);
        if(body != null){
            System.out.println(body);
            if (body.containsKey(ID)){
                final Deal deal = dealDAO.getDealById(body.get(ID));
                if (deal != null){
                    req.setAttribute(DEAL, deal);
                }
            } else if (body.containsKey(DEAL)){
                req.setAttribute(PRE, body.get(DEAL));
            } else if (body.containsKey(ORGANISATION)){
                final Deal deal = new Deal();
                deal.setDate(Date.valueOf(LocalDate.now()));
                deal.setType(DealType.sell);
                deal.setOrganisation(dao.getObjectById(Organisation.class, body.get(ORGANISATION)));
                req.setAttribute(DEAL, deal);
            }
        }
        req.setAttribute(TITLE, _TITLE);
        req.setAttribute(MODAL_CONTENT, _CONTENT);
        req.setAttribute(PRODUCTS, dao.getProductList());
        req.setAttribute(TYPES, DealType.values());
        req.setAttribute(ACTIONS, dao.getObjects(ProductAction.class));
        req.setAttribute(SHIPPERS, dao.getShipperList());
        req.setAttribute(UNITS, dao.getObjects(Unit.class));
        req.setAttribute(FIND_ORGANISATION, Branches.API.References.FIND_ORGANISATION);
        req.setAttribute(ORGANISATION_EDIT, Branches.UI.References.ORGANISATION_EDIT);
        req.setAttribute(PARSE_ORGANISATION, Branches.API.References.PARSE_ORGANISATION);
        req.setAttribute(SAVE, Branches.API.Transportation.DEAL_EDIT);
        show(req, resp);
    }
}
