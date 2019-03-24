package api.plan;

import api.IAPI;
import api.deal.DealListAPI;
import constants.Branches;
import constants.Constants;
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
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@WebServlet(Branches.API.PLAN_LIST)
public class LoadPlanListAPI extends IAPI{

    final HashMap<String, Object> parameters = new HashMap<>();
    final JSONObject array = new JSONObject();
    final JSONArray add = new JSONArray();
    final JSONArray update = new JSONArray();
    final JSONArray remove = new JSONArray();

    {
        array.put("add", add);
        array.put("update", update);
        array.put("remove", remove);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = PostUtil.parseBodyJson(req);
        long deal = (long) body.get(Constants.DEAL_ID);
        parameters.put("deal", deal);

        JSONObject plans = (JSONObject) body.get("plans");
        for (LoadPlan plan : hibernator.query(LoadPlan.class, parameters)){
            String id = String.valueOf(plan.getId());
            if (plans.containsKey(id)){
                long hash = (long) plans.remove(id);
                if (plan.hashCode() != hash){
                    update.add(JsonParser.toJson(plan));
                }
            } else {
                add.add(JsonParser.toJson(plan));
            }
        }

        PostUtil.write(resp, array.toJSONString());

        body.clear();
        add.clear();
        update.clear();
        remove.clear();

    }
}
