package api.references.driver;

import api.IAPI;
import constants.Branches;
import constants.Constants;
import entity.transport.Driver;
import org.json.simple.JSONArray;
import utils.JsonParser;
import utils.PostUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by szpt_user045 on 12.03.2019.
 */
@WebServlet(Branches.API.References.FIND_DRIVER)
public class FindDriverAPI extends IAPI{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HashMap<String, String> body = PostUtil.parseBody(req);
        String key = body.get(Constants.KEY);
        HashMap<Integer, Driver> result = new HashMap<>();

        find("person/forename", key, result);
        find("person/surname", key, result);
        find("person/patronymic", key, result);

        JSONArray array = result.values().stream().map(JsonParser::toJson).collect(Collectors.toCollection(JSONArray::new));

        write(resp, array.toJSONString());

        body.clear();
        array.clear();
    }
    synchronized void find(String key, String value, HashMap<Integer, Driver> result){
        for (Driver driver : hibernator.find(Driver.class, key, value)){
            result.put(driver.getId(), driver);
        }
    }
}
