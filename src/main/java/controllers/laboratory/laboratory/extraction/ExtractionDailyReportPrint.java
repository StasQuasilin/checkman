package controllers.laboratory.laboratory.extraction;

import constants.Branches;
import controllers.IModal;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 17.05.2019.
 */
@WebServlet(Branches.UI.Extraction.DAILY_REPORT_PRINT)
public class ExtractionDailyReportPrint extends IModal {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("title", "title.extraction.daily.report.print");
        req.setAttribute("modalContent", "/pages/laboratory/subdivisions/extraction/reports/extractionDailyReport.jsp");
        req.setAttribute("print", Branches.API.EXTRACTION_DAILY_REPORT_PRINT);
        show(req, resp);
    }
}
