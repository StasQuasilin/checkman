package api.references.driver;

import api.ServletAPI;
import constants.Branches;
import constants.Constants;
import entity.Person;
import entity.documents.LoadPlan;
import entity.transport.Driver;
import org.json.simple.JSONObject;
import utils.Parser;
import utils.UpdateUtil;
import utils.VehicleParser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by szpt_user045 on 12.03.2019.
 */
@WebServlet(Branches.API.PARSE_PERSON)
public class ParsePersonServletAPI extends ServletAPI {
    private static final long serialVersionUID = 8909201810599179679L;
    private final UpdateUtil updateUtil = new UpdateUtil();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if(body != null) {
            String key = String.valueOf(body.get(Constants.KEY));
            Driver driver = VehicleParser.parseDriver(key);
            dao.save(driver.getPerson());
            dao.save(driver);
            write(resp, SUCCESS_ANSWER);
            if (body.containsKey(Constants.TRANSPORTATION)){
                LoadPlan plan = dao.getLoadPlanById(body.get(Constants.TRANSPORTATION));
                plan.getTransportation().setDriver(driver);
                dao.save(plan.getTransportation());
                updateUtil.onSave(plan.getTransportation());
            }
        }
    }
}
