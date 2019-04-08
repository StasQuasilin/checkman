package api.archive;

import api.IAPI;
import constants.Branches;
import entity.documents.Deal;
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
@WebServlet(Branches.API.Archive.DEALS)
public class DealArchiveAPI extends IAPI {
    final JSONArray array = new JSONArray();
    final HashMap<String, Object> parameters = new HashMap<>();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = PostUtil.parseBodyJson(req);

        parameters.put("archive", true);
        List<Deal> deals = hibernator.query(Deal.class, parameters);

        array.addAll(deals.stream().map(JsonParser::toJson).collect(Collectors.toList()));

        write(resp, array.toJSONString());
        array.clear();
        parameters.clear();
    }
}
