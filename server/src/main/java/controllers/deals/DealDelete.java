package controllers.deals;

import constants.Branches;
import constants.Constants;
import constants.Titles;
import controllers.IModal;
import entity.documents.Deal;
import org.json.simple.JSONObject;
import utils.PostUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 29.03.2019.
 */
@WebServlet(Branches.UI.DEAL_DELETE)
public class DealDelete extends IModal {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = PostUtil.parseBodyJson(req);
        if (body != null) {
            Deal deal = dao.getDealById(body.get(Constants.ID));
            req.setAttribute("deal", deal);
            req.setAttribute("done", dao.getLoadPlanByDeal(deal, true, null));
            req.setAttribute("loads", dao.getLoadPlanByDeal(deal, false, null));

            req.setAttribute("title", Titles.DEAL_DELETE);
            req.setAttribute("delete", Branches.API.DEAL_DELETE);
            req.setAttribute("modalContent", "/pages/deals/dealDelete.jsp");
            show(req, resp);
        }
    }
}
