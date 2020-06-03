package api.reports;

import api.ServletAPI;
import constants.ApiLinks;
import constants.Keys;
import entity.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.hibernate.dao.ProductDAO;
import utils.hibernate.dao.ReportDAO;
import utils.hibernate.dao.UserDAO;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;

import static constants.Keys.*;

@WebServlet(ApiLinks.REPORT_SAVE)
public class SaveReportAPI extends ServletAPI {

    private final UserDAO userDAO = new UserDAO();
    private final ReportDAO reportDAO = new ReportDAO();
    private final ProductDAO productDAO = new ProductDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        JSONObject body = parseBody(req);
        ServerAnswer answer;
        if (body != null) {
            System.out.println(body);
            final String header = req.getHeader(TOKEN);
            final User user = userDAO.getUserByToken(header);
            Report report = reportDAO.getReportByUUID(body.get(Keys.UUID));

            if (report == null){
                report = new Report();
                report.setOwner(user);
            }

            Timestamp leave = new Timestamp(Long.parseLong(String.valueOf(body.get(LEAVE))));
            report.setLeaveTime(leave);

            final Product product = productDAO.getProduct(body.get(PRODUCT));
            report.setProduct(product);

            if (body.containsKey(ROUTE)) {
                JSONObject route = (JSONObject) body.get(ROUTE);
                if (route.containsKey(ROUTE)){
                    JSONArray routeArray = (JSONArray) route.get(ROUTE);
                    StringBuilder stringBuilder = new StringBuilder();
                    int i = 0;
                    for (Object o : routeArray){
                        stringBuilder.append(o);
                        if (i < routeArray.size() - 1){
                            stringBuilder.append(COMA);
                        }
                    }
                    report.setRoute(stringBuilder.toString());
                }
            }

            report.setUuid(String.valueOf(body.get(Keys.UUID)));

            reportDAO.save(report);

            answer = new SuccessAnswer();
            answer.addParam(ID, report.getId());
            answer.addParam(Keys.UUID, report.getUuid());
            write(resp, answer.toJson());

        }
    }
}

