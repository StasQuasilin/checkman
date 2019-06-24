package api.references.driver;

import api.API;
import constants.Branches;
import constants.Constants;
import entity.transport.Driver;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.JsonParser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * Created by szpt_user045 on 12.03.2019.
 */
@WebServlet(Branches.API.References.FIND_DRIVER)
public class FindDriverAPI extends API {


    final Logger log = Logger.getLogger(FindDriverAPI.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            final JSONArray array = new JSONArray();
            String key = (String) body.get(Constants.KEY);
            key = key.trim().replaceAll("  ", " ");
            log.info("Find driver \'" + key + "\'");


            array.addAll(dao.findDriver(key).stream().map(JsonParser::toJson).collect(Collectors.toCollection(JSONArray::new)));

            write(resp, array.toJSONString());

            body.clear();
            array.clear();
        } else {
            write(resp, emptyBody);
        }
    }
}
