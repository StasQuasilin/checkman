package api.weight;

import api.API;
import constants.Branches;
import entity.documents.LoadPlan;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.JsonParser;
import utils.JsonPool;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@WebServlet(Branches.API.WEIGHT_LIST)
public class WeightListAPI extends API {

    final JsonPool pool = JsonPool.getPool();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JSONObject array = pool.getObject();
        final JSONArray add = pool.getArray();
        final JSONArray update = pool.getArray();
        final JSONArray remove = pool.getArray();
        array.put(ADD, add);
        array.put(UPDATE, update);
        array.put(REMOVE, remove);

        JSONObject body = parseBody(req);

        if (body != null) {
            String id;
            for (LoadPlan loadPlan : dao.getActiveTransportations(null)) {
                id = String.valueOf(loadPlan.getId());
                if (body.containsKey(id)) {
                    long hash = (long) body.remove(id);
                    if (hash != loadPlan.hashCode()) {
                        update.add(JsonParser.toLogisticJson(loadPlan));
                    }
                } else {
                    add.add(JsonParser.toLogisticJson(loadPlan));
                }
            }
            remove.addAll((Collection) body.keySet().stream().map(key -> Integer.parseInt((String) key)).collect(Collectors.toList()));
        }
        write(resp, add.size() == 0 && update.size() == 0 && remove.size() == 0 ? EMPTY : array.toJSONString());
        pool.put(array);
    }
}
