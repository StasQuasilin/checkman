package controllers.deals;

import constants.Branches;
import constants.Constants;
import controllers.IModal;
import entity.DealType;
import entity.Worker;
import entity.documents.Deal;
import entity.organisations.Organisation;
import entity.products.ProductAction;
import entity.transport.TransportCustomer;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import utils.PostUtil;
import utils.hibernate.dao.DealDAO;
import utils.json.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

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
        JsonObject body = parseBodyGood(req);
        long id = -1;
        if (body != null) {
            System.out.println(body);
            if (body.contain(ID)) {
                id = body.getInt(ID);
                final Deal dealById = dealDAO.getDealById(id);
                System.out.println(dealById);
                req.setAttribute(DEAL, dealDAO.getDealById(id));
                req.setAttribute(TITLE, Constants.Languages.DEAL_EDIT);
            } else if (body.contain(COPY)) {
                id = body.getInt(COPY);
                Deal deal = dealDAO.getDealById(id);
                deal.setId(-1);
                deal.setComplete(0);
                req.setAttribute(DEAL, deal);
                req.setAttribute(TITLE, Constants.Languages.DEAL_COPY);
            } else if (body.contain(DEAL)){
                req.setAttribute(PRE, body.getObject(DEAL));
            }
        }

        if (id == -1) {
            req.setAttribute(TITLE, Constants.Languages.DEAL_CREATE);
        }

        req.setAttribute(ACTIONS, dao.getObjects(ProductAction.class));
        final String parameter = req.getParameter(TYPE);
        if(parameter != null){
            req.setAttribute(TYPE, parameter);
        } else {
            req.setAttribute(TYPE, DealType.sell);
        }

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
