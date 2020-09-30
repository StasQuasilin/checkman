package controllers.api.reports;

import constants.Apis;
import constants.Keys;
import controllers.api.Api;
import entity.reports.Report;
import utils.db.DaoService;
import utils.db.dao.reports.ReportDAO;
import utils.json.JsonObject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(Apis.EDIT_REPORT)
public class EditReportApi extends Api {

    private final ReportDAO reportDAO = DaoService.getReportDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final JsonObject body = parseBody(req);
        if (body != null){
            Report report = reportDAO.getReportById(body.get(Keys.ID));
            if (report == null){
                report = new Report();
            }

            
        }
    }
}
