package controllers.deals;

import constants.Branches;
import constants.Constants;
import constants.Titles;
import controllers.IModal;
import controllers.IUIServlet;
import entity.documents.Deal;
import entity.transport.TransportCustomer;
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
@WebServlet(Branches.UI.DEAL_SHOW)
public class DealShow extends IUIServlet {

    private DealDAO dealDAO = new DealDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int dealId = -1;
        JSONObject body = parseBody(req);
        String parameterId = req.getParameter(Constants.ID);
        if (parameterId != null) {
            dealId = Integer.parseInt(parameterId);
        } else {
            if (body != null && body.containsKey(Constants.ID)){
                dealId = Integer.parseInt(String.valueOf(body.remove(Constants.ID)));
            }
        }

        if (dealId != -1) {
            if (body != null && body.containsKey(BACK)){
                req.setAttribute(BACK, body.get(BACK));
                if(body.containsKey(ATTRIBUTES)){
                    req.setAttribute(ATTRIBUTES, body.get(ATTRIBUTES));
                }
            }

            req.setAttribute(DEAL, dealDAO.getDealById(dealId));
            req.setAttribute(TITLE, Titles.DEAL_SHOW);
            req.setAttribute(SAVE, Branches.API.PLAN_LIST_SAVE);
            req.setAttribute(REMOVE, Branches.API.REMOVE_PLAN);
            req.setAttribute(TRANSPORTATIONS, Branches.API.DEAL_TRANSPORTATIONS);
            req.setAttribute(FIND_ORGANISATION, Branches.API.References.FIND_ORGANISATION);
            req.setAttribute(PARSE_ORGANISATION, Branches.API.References.PARSE_ORGANISATION);
            req.setAttribute(EDIT_ORGANISATION, Branches.UI.References.ORGANISATION_EDIT);

            req.setAttribute(FIND_VEHICLE, Branches.API.References.FIND_VEHICLE);
            req.setAttribute(PARSE_VEHICLE, Branches.API.PARSE_VEHICLE);
            req.setAttribute(EDIT_VEHICLE, Branches.UI.EDIT_VEHICLE);

            req.setAttribute(FIND_DRIVER, Branches.API.References.FIND_DRIVER);
            req.setAttribute(PARSE_DRIVER, Branches.API.PARSE_PERSON);
            req.setAttribute(EDIT_DRIVER, Branches.UI.EDIT_DRIVER);

            req.setAttribute(FIND_TRAILER, Branches.API.References.FIND_TRAILER);
            req.setAttribute(PARSE_TRAILER, Branches.API.PARSE_TRAILER);
            req.setAttribute(MODAL_CONTENT, "/pages/deals/dealShow.jsp");
            req.setAttribute(CONTENT, "/pages/deals/dealShow.jsp");
            req.setAttribute(CUSTOMERS, new TransportCustomer[]{TransportCustomer.szpt, TransportCustomer.cont});
            show(req, resp);
        }

    }
}
