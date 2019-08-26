package controllers.archive;

import constants.Branches;
import constants.Titles;
import controllers.IUIServlet;
import entity.DealType;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 08.04.2019.
 */
@WebServlet(Branches.UI.DEAL_ARCHIVE)
public class DealArchive extends IUIServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DealType type = DealType.valueOf(req.getParameter("type"));
        req.setAttribute("title", Titles.Archive.DEAL + "." + type.toString());
        req.setAttribute("filter", "/pages/filters/archiveFilter.jsp");
        req.setAttribute("show", "");
        req.setAttribute("type", type.toString());
        req.setAttribute("content", "/pages/deals/dealList.jsp");
        req.setAttribute("subscribe", "DEAL_" + type.toString().toUpperCase() + "_ARCHIVE");
        req.setAttribute("limit", 14);
        show(req, resp);
    }
}
