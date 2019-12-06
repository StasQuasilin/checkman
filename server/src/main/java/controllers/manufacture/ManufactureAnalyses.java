package controllers.manufacture;

import constants.Branches;
import controllers.IUIServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 05.12.2019.
 */
@WebServlet(Branches.UI.MANUFACTURE_ANALYSES)
public class ManufactureAnalyses extends IUIServlet {
    private static final String _TITLE = "title.manufacture.analyse";
    private static final String _CONTENT = "/pages/manufacture/manufactureAnalyses.jsp";
    private static final String _FILTER = "/pages/manufacture/manufactureAnalysesFilter.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(TITLE, _TITLE);
        req.setAttribute(CONTENT, _CONTENT);
        req.setAttribute(UPDATE, Branches.API.MANUFACTURE_ANALYSE);
        req.setAttribute(FILTER, _FILTER);
        show(req, resp);
    }
}
