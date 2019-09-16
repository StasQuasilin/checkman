package controllers.reports;

import api.sockets.Subscriber;
import constants.Branches;
import controllers.IUIServlet;

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

    final Subscriber[] subscribe = new Subscriber[]{Subscriber.MANUFACTURE_REPORTS};

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("title", "title.manufacture.reports");
        req.setAttribute("content", "/pages/reports/manufactureReportList.jsp");
        req.setAttribute("filter", "/pages/filters/archiveFilter.jsp");
        req.setAttribute("edit", Branches.UI.MANUFACTURE_REPORT_EDIT);
        req.setAttribute("subscribe", subscribe);
        show(req, resp);
    }
}
