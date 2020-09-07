package controllers.deals;

import constants.Apis;
import constants.Urls;
import controllers.Modal;
import entity.deals.Deal;
import entity.deals.DealType;
import utils.db.dao.DaoService;
import utils.db.dao.deals.DealDAO;
import utils.db.dao.references.ProductDAO;
import utils.json.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import static constants.Urls.*;
import static constants.Keys.*;

@WebServlet(Urls.DEAL_EDIT)
public class DealEdit extends Modal {

    private static final String _TITLE = "deal.edit";
    private static final String _CONTENT = "/pages/deals/dealEdit.jsp";
    private final DealDAO dealDAO = DaoService.getDealDAO();
    private final ProductDAO productDAO = DaoService.getProductDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JsonObject body = parseBody(req);
        if (body != null){
            if (body.contains(ID)){
                final Object id = body.get(ID);
                final Deal deal = dealDAO.getDealById(id);
                if (deal != null){
                    req.setAttribute(DEAL, deal);
                }
            }
        }
        final String type = req.getParameter(TYPE);
        req.setAttribute(TYPE, type);
        req.setAttribute(TYPES, DealType.values());
        req.setAttribute(TITLE, _TITLE);
        req.setAttribute(PRODUCTS, productDAO.getProducts());
        req.setAttribute(CONTENT, _CONTENT);
        req.setAttribute(SAVE, Apis.DEAL_EDIT);
        req.setAttribute(PRODUCT_EDIT, Urls.EDIT_PRODUCT);
        req.setAttribute(FIND_ORGANISATION, Apis.FIND_ORGANISATION);
        show(req, resp);
    }
}
