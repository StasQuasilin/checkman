package api.references.driver;

import api.ServletAPI;
import constants.Branches;
import constants.Constants;
import entity.Person;
import entity.documents.LoadPlan;
import entity.transport.Driver;
import entity.transport.Transportation2;
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
            JSONObject object = parser.toJson(new SuccessAnswer("driver", parser.toJson(driver)));
            String s = object.toJSONString();
            write(resp, s);
            pool.put(object);
            if (body.containsKey(Constants.TRANSPORTATION)){
                Transportation2 transportation2 = dao.getObjectById(Transportation2.class, body.get(Constants.TRANSPORTATION));
                transportation2.setDriver(driver);
                dao.save(transportation2);
                updateUtil.onSave(transportation2);
            }
        }
    }
}
