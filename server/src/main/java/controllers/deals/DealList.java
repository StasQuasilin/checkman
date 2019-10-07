package controllers.deals;

import constants.Branches;
import constants.Titles;
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
        req.setAttribute(TITLE, Titles.DEAL_LIST + '-' + type);
        req.setAttribute(CONTENT, "/pages/deals/dealList.jsp");
        req.setAttribute("filter", "/pages/filters/dealFilter.jsp");
        req.setAttribute("type", type);
        req.setAttribute("show", Branches.UI.DEAL_SHOW);
        req.setAttribute("edit", Branches.UI.DEAL_EDIT + "?type=" + type );
        req.setAttribute("delete", Branches.UI.DEAL_DELETE);
        req.setAttribute("subscribe", "DEAL_" + type.toUpperCase());
        req.setAttribute("limit", -1);
        show(req, resp);
    }
}
