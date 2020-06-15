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

    private static final String FILTER_PAGE = "/pages/filters/archiveFilter.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DealType type = DealType.valueOf(req.getParameter("type"));
        req.setAttribute(TITLE, Titles.Archive.DEAL + "." + type.toString());
        req.setAttribute(FILTER, FILTER_PAGE);
        req.setAttribute("show", "");
        req.setAttribute(TYPE, type.toString());
        req.setAttribute(CONTENT, "/pages/deals/dealList.jsp");
        req.setAttribute(SUBSCRIBE, "DEAL_" + type.toString().toUpperCase() + "_ARCHIVE");
        show(req, resp);
    }
}
