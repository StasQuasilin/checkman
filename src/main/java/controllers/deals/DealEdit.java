package controllers.deals;

import constants.Branches;
import constants.Constants;
import controllers.IModal;
import entity.DealType;
import entity.Worker;
import entity.documents.Deal;
import entity.products.ProductAction;
import entity.transport.TransportCustomer;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import utils.PostUtil;
import utils.hibernate.dao.DealDAO;

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

    private static final String _CONTENT = "/pages/deals/dealEdit.jsp";
    private DealDAO dealDAO = new DealDAO();
    private final Logger log = Logger.getLogger(DealEdit.class);
    private final TransportCustomer[] customers = new TransportCustomer[]{
            TransportCustomer.szpt,
            TransportCustomer.cont
    };

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
            req.setAttribute(DEAL, dealDAO.getDealById(id));
            req.setAttribute(TITLE, Constants.Languages.DEAL_EDIT);
        } else if (copy != -1){
            Deal deal = dealDAO.getDealById(copy);
            deal.setId(-1);
            deal.setComplete(0);
            req.setAttribute(DEAL, deal);
            req.setAttribute(TITLE, Constants.Languages.DEAL_COPY);
        } else {
            req.setAttribute(TITLE, Constants.Languages.DEAL_CREATE);
        }

        req.setAttribute(ACTIONS, dao.getObjects(ProductAction.class));
        req.setAttribute(TYPE, req.getParameter(TYPE));
        req.setAttribute(TYPES, DealType.values());
        req.setAttribute(PRODUCTS, dao.getProductList());
        req.setAttribute(SHIPPERS, dao.getShipperList());
        req.setAttribute(CUSTOMERS, customers);
        req.setAttribute(UNITS, dao.getWeightUnits());
        req.setAttribute(FIND_ORGANISATION, Branches.API.References.FIND_ORGANISATION);
        req.setAttribute(PARSE_ORGANISATION, Branches.API.References.PARSE_ORGANISATION);
        req.setAttribute(EDIT_ORGANISATION, Branches.UI.References.ORGANISATION_EDIT);
        req.setAttribute(SAVE, Branches.API.DEAL_SAVE);
        req.setAttribute("redirect", Branches.UI.DEAL_SHOW);
        req.setAttribute(MODAL_CONTENT, _CONTENT);
        show(req, resp);
    }
}
