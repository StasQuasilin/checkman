package api.laboratory;

import api.API;
import constants.Branches;
import entity.DealType;
import entity.documents.LoadPlan;
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

/**
 * Created by szpt_user045 on 27.03.2019.
 */
@WebServlet(Branches.API.LABORATORY_LIST)
public class LaboratoryListAPI extends API {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final HashMap<String, Object> parameters = new HashMap<>();
        final JSONObject array  = new JSONObject();
        final JSONArray add = new JSONArray();
        final JSONArray update = new JSONArray();
        final JSONArray remove = new JSONArray();
        parameters.put("transportation/archive", false);
        array.put("add", add);
        array.put("update", update);
        array.put("remove", remove);

        JSONObject body = PostUtil.parseBodyJson(req);
        if (body != null) {
            String type = req.getParameter("type");
            parameters.put("deal/type", DealType.valueOf(type));

            for (LoadPlan plan : hibernator.query(LoadPlan.class, parameters)) {
                String id = String.valueOf(plan.id);
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
        write(resp, array.toJSONString());

        add.clear();
        update.clear();
        remove.clear();
    }
}
