package api.references;

import api.API;
import constants.Branches;
import constants.Constants;
import entity.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.JsonParser;
import utils.PostUtil;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@WebServlet(Branches.API.References.FIND_WORKER)
public class FindWorkerAPI extends API {

    final JSONArray array = new JSONArray();


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            Object key = body.get(Constants.KEY);

            array.addAll(dao.findWorker(key).stream().map(JsonParser::toJson).collect(Collectors.toList()));

            PostUtil.write(resp, array.toJSONString());
            body.clear();
            array.clear();
        }

    }
}
