package api.logistic;

import api.API;
import constants.Branches;
import entity.documents.LoadPlan;
import entity.transport.TransportCustomer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.ApplicationSettingsBox;
import utils.JsonParser;
import utils.JsonPool;
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
    TransportCustomer customer = settingsBox.getSettings().getCustomer();
    final JsonPool pool = JsonPool.getPool();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        final JSONObject array = pool.getObject();
        final JSONArray add = pool.getArray();
        final JSONArray update = pool.getArray();
        final JSONArray remove = pool.getArray();

        array.put("add", add);
        array.put("update", update);
        array.put("remove", remove);

        if (body != null) {
            List<LoadPlan> loadPlans = dao.getTransportationsByCustomer(customer);
            String id;
            for (LoadPlan loadPlan : loadPlans) {
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
        write(resp, array.toJSONString());
        pool.put(array);
        add.clear();
        update.clear();
        remove.clear();
    }
}
