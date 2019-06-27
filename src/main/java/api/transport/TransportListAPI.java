package api.transport;

import api.API;
import constants.Branches;
import entity.documents.LoadPlan;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.JsonParser;
import utils.JsonPool;
import utils.PostUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@WebServlet(Branches.API.TRANSPORT_LIST)
public class TransportListAPI extends API {

    final JsonPool pool = JsonPool.getPool();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        final JSONObject array = pool.getObject();
        final JSONArray add = pool.getArray();
        final JSONArray update = pool.getArray();
        final JSONArray remove = pool.getArray();

        array.put(ADD, add);
        array.put(UPDATE, update);
        array.put(REMOVE, remove);

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

            for (Object key : body.keySet()) {
                remove.add(Integer.parseInt((String) key));
            }
        }

        write(resp, add.size() == 0 && update.size() == 0 && remove.size() == 0 ? EMPTY : array.toJSONString());
        pool.put(array);
    }
}
