package api.references.driver;

import api.ServletAPI;
import constants.Branches;
import entity.transport.Driver;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 12.03.2019.
 */
@WebServlet(Branches.API.References.FIND_DRIVER)
public class FindDriverServletAPI extends ServletAPI {

    final Logger log = Logger.getLogger(FindDriverServletAPI.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            String key = String.valueOf(body.get(KEY));

            JSONArray array = pool.getArray();
            for (Driver driver : dao.findDriver(key)){
                array.add(driver.toJson());
            }

            write(resp, array.toJSONString());

            body.clear();
            array.clear();
        } else {
            write(resp, EMPTY_BODY);
        }
    }
}
