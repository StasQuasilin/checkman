package api.weight.report;

import api.sockets.Subscriber;
import constants.Branches;
import controllers.IUIServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(Branches.UI.ROUND_REPORTS)
public class RoundReportList extends IUIServlet {
    private static final String _TITLE = "title.round.reports.list";
    private static final String _CONTENT = "/pages/weight/reporter/reportList.jsp";
    private static final Subscriber[] subscriber = new Subscriber[]{Subscriber.ROUND_REPORT};
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(TITLE, _TITLE);
        req.setAttribute(CONTENT, _CONTENT);
        req.setAttribute(ADD, Branches.UI.EDIT_REPORT);
        req.setAttribute(SUBSCRIBE, subscriber);
        req.setAttribute(LIMIT, 14);
        show(req, resp);
    }
}
