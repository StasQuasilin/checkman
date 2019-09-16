package controllers.reports;

import constants.Branches;
import controllers.IModal;
import utils.turns.TurnBox;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 16.09.2019.
 */
@WebServlet(Branches.UI.MANUFACTURE_REPORT_EDIT)
public class ReportEdit extends IModal {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("title", "title.manufacture.reports.edit");
        req.setAttribute("modalContent", "/pages/reports/manufactureReportEdit.jsp");
        req.setAttribute("turns", TurnBox.getTurns());
        req.setAttribute("fields", dao.getReportFields());
        req.setAttribute("save", Branches.API.SAVE_MANUFACTURE_REPORT);
        show(req, resp);
    }
}
