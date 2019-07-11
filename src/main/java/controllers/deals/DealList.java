package controllers.deals;

import constants.Branches;
import constants.Constants;
import controllers.IServlet;
import controllers.IUIServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@WebServlet(Branches.UI.DEAL_LIST)
public class DealList extends IUIServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String type = req.getParameter("type");
        req.setAttribute("title", Constants.Titles.DEAL_LIST + '-' + type);
        req.setAttribute("content", "/pages/deals/dealList.jsp");
        req.setAttribute("filter", "/pages/filters/dealFilter.jsp");
        req.setAttribute("update", Branches.API.DEAL_LIST );
        req.setAttribute("type", type);
        req.setAttribute("show", Branches.UI.DEAL_SHOW);
        req.setAttribute("edit", Branches.UI.DEAL_EDIT + "?type=" + type );
        req.setAttribute("delete", Branches.UI.DEAL_DELETE);
        req.setAttribute("subscribe", "DEAL_" + type.toUpperCase());
        show(req, resp);
    }
}
