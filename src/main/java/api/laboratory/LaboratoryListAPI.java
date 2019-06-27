package api.laboratory;

import api.API;
import constants.Branches;
import constants.Constants;
import entity.DealType;
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
import java.util.HashMap;

/**
 * Created by szpt_user045 on 27.03.2019.
 */
@WebServlet(Branches.API.LABORATORY_LIST)
public class LaboratoryListAPI extends API {

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

        JSONObject body = PostUtil.parseBodyJson(req);
        if (body != null) {
            String type = req.getParameter(Constants.TYPE);

            String id;
            for (LoadPlan plan : dao.getLoadPlansByDealType(DealType.valueOf(type))) {
                id = String.valueOf(plan.id);
                if (body.containsKey(id)) {
                    long hash = (long) body.remove(id);
                    if (plan.hashCode() != hash) {
                        update.add(JsonParser.toLogisticJson(plan));
                    }
                } else {
                    add.add(JsonParser.toLogisticJson(plan));
                }
            }
            for (Object key : body.keySet()) {
                remove.add(Integer.parseInt(String.valueOf(key)));
            }
            body.clear();
        }
        write(resp, add.size() == 0 && update.size() == 0 && remove.size() == 0 ? EMPTY : array.toJSONString());
        pool.put(array);
    }
}
