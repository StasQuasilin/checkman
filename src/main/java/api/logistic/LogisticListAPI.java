package api.logistic;

import api.API;
import constants.Branches;
import entity.documents.LoadPlan;
import entity.transport.TransportCustomer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.ApplicationSettingsBox;
import utils.JsonParser;
import utils.PostUtil;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by quasilin on 18.03.2019.
 */
@WebServlet(urlPatterns = Branches.API.LOGISTIC_LIST, name = "Logistic list")
public class LogisticListAPI extends API {

    final ApplicationSettingsBox settingsBox = ApplicationSettingsBox.getBox();
    final HashMap<String, Object> parameters = new HashMap<>();

    {
        TransportCustomer customer = settingsBox.getSettings().getCustomer();
        if (customer != null) {
            parameters.put("customer", customer);
        }
        parameters.put("transportation/archive", false);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = PostUtil.parseBodyJson(req);
        final JSONObject array = new JSONObject();
        final JSONArray add = new JSONArray();
        final JSONArray update = new JSONArray();
        final JSONArray remove = new JSONArray();

        array.put("add", add);
        array.put("update", update);
        array.put("remove", remove);

        if (body != null) {
            List<LoadPlan> loadPlans = hibernator.query(LoadPlan.class, parameters);
            for (LoadPlan loadPlan : loadPlans) {
                String id = String.valueOf(loadPlan.getId());
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
        PostUtil.write(resp, array.toJSONString());
        add.clear();
        update.clear();
        remove.clear();
        body.clear();
    }
}
