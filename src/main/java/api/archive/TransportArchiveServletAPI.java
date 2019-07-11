package api.archive;

import api.ServletAPI;
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

/**
 * Created by szpt_user045 on 12.04.2019.
 */
@WebServlet(Branches.API.TRANSPORT_ARCHIVE)
public class TransportArchiveServletAPI extends ServletAPI {

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
            for (LoadPlan plan : dao.getTransportArchive()) {
                String id = String.valueOf(plan.getId());
                if (body.containsKey(id)) {
                    long hash = (long) body.remove(id);
                    if (plan.hashCode() != hash) {
                        update.add(parser.toLogisticJson(plan));
                    }
                } else {
                    add.add(parser.toLogisticJson(plan));
                }
            }

            for (Object o : body.keySet()) {
                remove.add(Integer.parseInt(String.valueOf(o)));
            }
        }
        write(resp, array.toJSONString());
        pool.put(array);
    }
}
