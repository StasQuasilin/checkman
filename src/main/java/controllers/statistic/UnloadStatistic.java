package controllers.statistic;

import constants.Branches;
import controllers.IUIServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 25.02.2020.
 */
@WebServlet(Branches.UI.UNLOAD_STATISTIC_LIST)
public class UnloadStatistic extends IUIServlet {

    private static final String _TITLE = "title.unload.statistic";
    private static final String _CONTENT = "/pages/statistic/unloadStatisticList.jsp";
    private static final String _FILTER = "/pages/filters/statisticFilter.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(TITLE, _TITLE);
        req.setAttribute(CONTENT, _CONTENT);
        req.setAttribute(PRODUCTS, dao.getProductList());
        req.setAttribute(FIND_ORGANISATION, Branches.API.References.FIND_ORGANISATION);
        req.setAttribute(FIND, Branches.API.GET_STATISTIC);
        req.setAttribute(FILTER, _FILTER);
        show(req, resp);
    }
}
