package api.references.driver;

import api.ServletAPI;
import constants.Branches;
import constants.Constants;
import entity.Person;
import entity.documents.LoadPlan;
import entity.transport.Driver;
import entity.transport.TransportUtil;
import org.json.simple.JSONObject;
import utils.Parser;
import utils.UpdateUtil;
import utils.VehicleParser;
import utils.answers.SuccessAnswer;

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
public class ParseDriverServletAPI extends ServletAPI {

    private final UpdateUtil updateUtil = new UpdateUtil();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if(body != null) {
            String key = String.valueOf(body.get(Constants.KEY));
            Driver driver = VehicleParser.parseDriver(key);

            JSONObject object = parser.toJson(new SuccessAnswer("driver", parser.toJson(driver)));
            write(resp, object.toJSONString());
            pool.put(object);
            if (body.containsKey(Constants.TRANSPORTATION)){
                LoadPlan plan = dao.getLoadPlanById(body.get(Constants.TRANSPORTATION));
                TransportUtil.setDriver(plan.getTransportation(), driver);
                dao.save(plan.getTransportation());
                updateUtil.onSave(plan.getTransportation());
            }
        }
    }
}
