package api.plan;

import api.ServletAPI;
import constants.Branches;
import constants.Constants;
import entity.documents.LoadPlan;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@WebServlet(Branches.API.PLAN_LIST)
public class LoadPlanListServletAPI extends ServletAPI {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final HashMap<String, Object> parameters = new HashMap<>();
        final JSONObject array = new JSONObject();
        final JSONArray update = new JSONArray();
        {
            array.put("update", update);
        }

        JSONObject body = parseBody(req);
        if (body != null) {
            long deal = (long) body.get(Constants.DEAL_ID);
            parameters.put("deal", deal);

            JSONObject plans = (JSONObject) body.get("plans");
            for (LoadPlan plan : dao.getLoadPlanByDeal(deal, null, null)) {
                String id = String.valueOf(plan.getId());
                if (plans.containsKey(id)) {
                    long hash = (long) plans.remove(id);
                    if (plan.hashCode() != hash) {
                        update.add(parser.toJson(plan));
                    }
                } else {
                    update.add(parser.toJson(plan));
                }
            }
        }
        write(resp, array.toJSONString());

        body.clear();
        update.clear();

    }
}
