package controllers.summary;

import constants.Branches;
import controllers.IModal;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 28.10.2019.
 */
@WebServlet(Branches.UI.SUMMARY_PLAN_PRINT)
public class SummaryPrint extends IModal {
    private static final String _CONTENT = "/pages/summary/summaryPrint.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(TITLE, "title.summary.print");
        req.setAttribute(MODAL_CONTENT, _CONTENT);
        show(req, resp);
    }
}
