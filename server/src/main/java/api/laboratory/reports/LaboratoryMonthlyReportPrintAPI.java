package api.laboratory.reports;

import constants.Branches;
import controllers.IModal;
import entity.documents.LoadPlan;
import org.json.simple.JSONObject;
import utils.PostUtil;
import utils.hibernate.DateContainers.BETWEEN;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by szpt_user045 on 06.06.2019.
 */
@WebServlet(Branches.API.LABORATORY_MONTHLY_REPORT)
public class LaboratoryMonthlyReportPrintAPI extends IModal {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject json = PostUtil.parseBodyJson(req);
        if (json != null) {
            JSONObject parameters = (JSONObject) json.get("parameters");
            final LocalDate date = LocalDate.parse(String.valueOf(parameters.get("date")));
            List<LoadPlan> plans = dao.getLoadPlansBetweenDates(date, date.plusMonths(1));
            req.setAttribute("plans", plans);
            req.getRequestDispatcher("/pages/laboratory/reports/print/monthlyReport.jsp").forward(req, resp);
        }
    }
}
