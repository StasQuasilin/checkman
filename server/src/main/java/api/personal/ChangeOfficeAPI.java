package api.personal;

import api.ServletAPI;
import constants.Branches;
import entity.Worker;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 16.09.2019.
 */
@WebServlet(Branches.API.CHANGE_OFFICE)
public class ChangeOfficeAPI extends ServletAPI {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            Worker worker = dao.getWorkerById(body.get("worker"));
            String office = String.valueOf(body.get("office"));
            worker.setOffice(office);
            dao.save(worker);
            write(resp, SUCCESS_ANSWER);
        }
    }
}
