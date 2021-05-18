package controllers.deals;

import constants.Branches;
import constants.Constants;
import constants.Titles;
import controllers.IModal;
import entity.documents.Deal;
import entity.transport.Transportation;
import org.json.simple.JSONObject;
import utils.PostUtil;
import utils.hibernate.dao.DealDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by szpt_user045 on 29.03.2019.
 */
@WebServlet(Branches.UI.DEAL_DELETE)
public class DealDelete extends IModal {

    private static final String _CONTENT = "/pages/deals/dealDelete.jsp";
    private DealDAO dealDAO = new DealDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            Deal deal = dealDAO.getDealById(body.get(Constants.ID));
            req.setAttribute(DEAL, deal);
            List<Transportation> done = dao.getTransportationByDeal(deal, true, null);
            req.setAttribute(DONE, done);
            req.setAttribute("loads", dao.getTransportationByDeal(deal, false, null));

            if (deal.getComplete() > 0 || done.size() > 0) {
                req.setAttribute(TITLE, Titles.DEAL_DELETE);
            } else {
                req.setAttribute(TITLE, Titles.DEAL_ARCHIVE);
            }
            req.setAttribute(DELETE, Branches.API.DEAL_DELETE);
            req.setAttribute(MODAL_CONTENT, _CONTENT);
            show(req, resp);
        }
    }
}
