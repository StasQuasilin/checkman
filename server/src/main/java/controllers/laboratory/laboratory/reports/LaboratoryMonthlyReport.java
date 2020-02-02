package controllers.laboratory.laboratory.reports;

import constants.Branches;
import controllers.IModal;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 06.06.2019.
 */
@WebServlet(Branches.UI.LABORATORY_PRINT)
public class LaboratoryMonthlyReport extends IModal {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(TITLE, "title.monthly.print");
        req.setAttribute(MODAL_CONTENT, "/pages/laboratory/reports/monthlyReport.jsp");
        req.setAttribute(PRINT, Branches.API.LABORATORY_MONTHLY_REPORT);
        show(req, resp);
    }
}
