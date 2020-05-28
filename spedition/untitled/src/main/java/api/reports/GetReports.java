package api.reports;

import api.ServletAPI;
import constants.ApiLinks;
import org.json.simple.JSONObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(ApiLinks.REPORT_SAVE)
public class GetReports extends ServletAPI {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final JSONObject body = parseBody(req);
        if(body != null){
            System.out.println(body);
            write(resp, SUCCESS_ANSWER);
        }
    }
}
