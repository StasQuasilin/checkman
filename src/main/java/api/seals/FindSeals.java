package api.seals;

import api.API;
import constants.Branches;
import constants.Constants;
import entity.seals.Seal;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
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
 * Created by szpt_user045 on 08.04.2019.
 */
@WebServlet(Branches.API.SEALS_FIND)
public class FindSeals extends API {
    final HashMap<String, String> parameters = new HashMap<>();
    {
        parameters.put("transportation", null);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = PostUtil.parseBodyJson(req);
        String key = String.valueOf(body.get(Constants.KEY));
        parameters.put("number", key);
        List<Seal> seals = hibernator.find(Seal.class, parameters);
        JSONArray array = seals.stream().map(JsonParser::toJson).collect(Collectors.toCollection(JSONArray::new));
        write(resp, array.toJSONString());
        body.clear();
        array.clear();
    }
}
