package controllers.api.reports;

import constants.Keys;
import controllers.api.Api;
import entity.reports.Report;
import entity.user.User;
import org.json.simple.JSONArray;
import utils.answers.Answer;
import utils.answers.SuccessAnswer;
import utils.db.DaoService;
import utils.db.dao.reports.ReportDAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GetReportsApi extends Api {

    private final ReportDAO reportDAO = DaoService.getReportDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final User user = getUser(req);

        final JSONArray array = new JSONArray();
        for (Report report : reportDAO.getReports(user)){
            array.add(report.toJson());
        }
        Answer answer = new SuccessAnswer();
        answer.addAttribute(Keys.RESULT, array);
        write(resp, answer);
    }
}
