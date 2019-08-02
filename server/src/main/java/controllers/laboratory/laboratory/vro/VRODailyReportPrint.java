package controllers.laboratory.laboratory.vro;

import constants.Branches;
import controllers.IModal;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 03.06.2019.
 */
@WebServlet(Branches.UI.VRO.DAILY_REPORT_PRINT)
public class VRODailyReportPrint extends IModal {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("title", "title.vro.daily.report.print");
        req.setAttribute("modalContent", "/pages/laboratory/subdivisions/vro/reports/vroDailyReport.jsp");
        req.setAttribute("print", Branches.API.VRO_DAILY_REPORT_PRINT);
        show(req, resp);
    }
}
