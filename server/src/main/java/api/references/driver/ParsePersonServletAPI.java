package api.references.driver;

import api.ServletAPI;
import constants.Branches;
import constants.Constants;
import entity.Person;
import entity.transport.Driver;
import org.json.simple.JSONObject;
import utils.Parser;
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
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if(body != null) {
            String key = String.valueOf(body.get(Constants.KEY));
            Driver driver = VehicleParser.parseDriver(key);
            JSONObject json = parser.toJson(driver);
            write(resp, json.toJSONString());
            pool.put(json);
        }
    }
}
