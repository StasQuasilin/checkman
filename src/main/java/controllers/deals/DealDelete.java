package controllers.deals;

import constants.Branches;
import constants.Constants;
import controllers.IModal;
import entity.documents.Deal;
import org.json.simple.JSONObject;
import utils.PostUtil;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;

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
            req.setAttribute("deal", dao.getDealById(body.get(Constants.ID)));
            req.setAttribute("title", Constants.Titles.DEAL_DELETE);
            req.setAttribute("deleteUrl", Branches.API.DEAL_DELETE);
            req.setAttribute("modalContent", "/pages/deals/dealDelete.jsp");
            show(req, resp);
        }
    }
}
