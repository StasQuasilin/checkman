package controllers.reports;

import api.sockets.Subscriber;
import constants.Branches;
import controllers.IUIServlet;
import utils.turns.TurnBox;
import utils.turns.TurnService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 16.09.2019.
 */
@WebServlet(Branches.UI.MANUFACTURE_REPORT)
public class ReportList extends IUIServlet {

    private static final String TURNS = "turns";
    private static final String BUILDER = "builder";
    final Subscriber[] subscribe = new Subscriber[]{Subscriber.MANUFACTURE_REPORTS};
    private static final String _TITLE = "title.manufacture.reports";
    private static final String _CONTENT = "/pages/reports/manufactureReportList.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(TITLE, _TITLE);
        req.setAttribute(CONTENT, _CONTENT);
        req.setAttribute(TURNS, TurnBox.getTurns());
        req.setAttribute(PRODUCTS, dao.getProductList());
        req.setAttribute(STORAGES, dao.getStorages());
        req.setAttribute(BUILDER, Branches.API.REPORT_BUILDER);
        req.setAttribute(FILTER, "/pages/filters/archiveFilter.jsp");
        req.setAttribute(EDIT, Branches.UI.MANUFACTURE_REPORT_EDIT);
        req.setAttribute(SUBSCRIBE, subscribe);
        show(req, resp);
    }
}
