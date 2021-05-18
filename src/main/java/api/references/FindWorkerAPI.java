package api.references;

import api.ServletAPI;
import constants.Branches;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
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
public class FindWorkerAPI extends ServletAPI {

    final JsonPool pool = JsonPool.getPool();
    final Logger log = Logger.getLogger(FindWorkerAPI.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            JSONArray array = pool.getArray();
            Object key = body.get(KEY);

            array.addAll(dao.findUser(key).stream().map(user -> user.toJson()).collect(Collectors.toList()));
            write(resp, array.toJSONString());
            body.clear();
            pool.put(array);
        }

    }
}
