package controllers.archive;

import constants.Branches;
import constants.Constants;
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
        req.setAttribute("title", Constants.Titles.Archive.DEAL + "." + type.toString());
        req.setAttribute("filter", "/pages/filters/archiveFilter.jsp");
        req.setAttribute("api", Branches.API.Archive.DEALS);
        req.setAttribute("type", type.toString());
        req.setAttribute("content", "/pages/archive/archiveList.jsp");
        show(req, resp);
    }
}
