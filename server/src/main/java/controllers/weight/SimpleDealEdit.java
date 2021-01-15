package controllers.weight;

import constants.Branches;
import controllers.IModal;
import entity.DealType;
import entity.documents.Deal;
import entity.products.ProductAction;
import entity.weight.Unit;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(Branches.UI.Transportation.DEAL_EDIT)
public class SimpleDealEdit extends IModal {

    private static final String _TITLE = "deal.edit";
    private static final String _CONTENT = "/pages/weight/dealEdit.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JSONObject body = parseBody(req);
        if(body != null){
            if (body.containsKey(ID)){
                final Deal deal = dao.getObjectById(Deal.class, body.get(ID));
                if (deal != null){
                    req.setAttribute(DEAL, deal);
                }
            } else if (body.containsKey(DEAL)){
                System.out.println(body.get(DEAL));
                req.setAttribute(PRE, body.get(DEAL));
            }
        }
        req.setAttribute(TITLE, _TITLE);
        req.setAttribute(MODAL_CONTENT, _CONTENT);
        req.setAttribute(PRODUCTS, dao.getProductList());
        req.setAttribute(TYPES, DealType.values());
        req.setAttribute(ACTIONS, dao.getObjects(ProductAction.class));
        req.setAttribute(SHIPPERS, dao.getShipperList());
        req.setAttribute(UNITS, dao.getObjects(Unit.class));
        req.setAttribute(SAVE, Branches.API.Transportation.DEAL_EDIT);
        show(req, resp);
    }
}
