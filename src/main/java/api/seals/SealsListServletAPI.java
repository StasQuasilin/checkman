package api.seals;

import api.ServletAPI;
import constants.Branches;
import entity.seals.SealBatch;
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
 * Created by szpt_user045 on 01.07.2019.
 */
@WebServlet(Branches.API.SEAL_UPDATE)
public class SealsListServletAPI extends ServletAPI {

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
            for (SealBatch batch : dao.getActiveSealsBatches()){
                id = String.valueOf(batch.getId());
                if (body.containsKey(id)){
                    long hash = (long) body.remove(id);
                    if (hash != batch.hashCode()){
                        update.add(parser.toJson(batch));
                    }
                } else {
                    add.add(parser.toJson(batch));
                }
            }
            for (Object o : body.keySet()){
                remove.add(Integer.parseInt(String.valueOf(o)));
            }
        } else {
            write(resp, emptyBody);
        }

        write(resp, array.toJSONString());
        pool.put(array);
    }
}
