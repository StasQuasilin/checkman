package api.references;

import api.ServletAPI;
import constants.Branches;
import constants.Constants;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.JsonParser;
import utils.JsonPool;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@WebServlet(Branches.API.References.FIND_WORKER)
public class FindWorkerServletAPI extends ServletAPI {

    final JsonPool pool = JsonPool.getPool();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            JSONArray array = pool.getArray();
            Object key = body.get(Constants.KEY);

            array.addAll(dao.findUser(key).stream().map(parser::toJson).collect(Collectors.toList()));

            write(resp, array.toJSONString());
            body.clear();
            array.clear();
        }

    }
}
