package api.reports;

import api.ServletAPI;
import constants.ApiLinks;
import org.json.simple.JSONObject;
import utils.hibernate.dao.ReportDAO;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static constants.Keys.USER;

@WebServlet(ApiLinks.REPORTS)
public class GetReports extends ServletAPI {

    private final ReportDAO reportDAO = new ReportDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final JSONObject body = parseBody(req);
        if(body != null){
            System.out.println(body);
//            reportDAO.getReports(body.get(USER));
            write(resp, SUCCESS_ANSWER);
        }
    }
}
