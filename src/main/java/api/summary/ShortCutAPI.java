package api.summary;

import api.API;
import constants.Branches;
import entity.documents.LoadPlan;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.JsonParser;
import utils.PostUtil;
import utils.hibernate.State;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by quasilin on 01.04.2019.
 */
@WebServlet(Branches.ShortCuts.UPDATE)
public class ShortCutAPI extends API {
    
    final JSONObject array = new JSONObject();
    final JSONObject territory = new JSONObject();
    final JSONObject cruise = new JSONObject();
    final JSONArray tAdd = new JSONArray();
    final JSONArray tUpdate = new JSONArray();
    final JSONArray tRemove = new JSONArray();
    final JSONArray cAdd = new JSONArray();
    final JSONArray cUpdate = new JSONArray();
    final JSONArray cRemove = new JSONArray();
    {
        array.put("territory", territory);
        territory.put("add", tAdd);
        territory.put("update", tUpdate);
        territory.put("remove", tRemove);
        array.put("cruise", cruise);
        cruise.put("add", cAdd);
        cruise.put("update", cUpdate);
        cruise.put("remove", cRemove);
    }

    final HashMap<String, Object> parameters = new HashMap<>();
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);

        if (body != null) {
            parameters.clear();
            parameters.put("transportation/timeIn", State.notNull);
            parameters.put("transportation/archive", false);
            doAction((JSONObject) body.get("territory"), dao.getTransportationsOnTerritory(), tAdd, tUpdate, tRemove);

            parameters.clear();
            parameters.put("transportation/timeOut", State.notNull);
            parameters.put("transportation/archive", false);
            doAction((JSONObject) body.get("cruise"), dao.getTransportationsOnCruise(), cAdd, cUpdate, cRemove);

            write(resp, array.toJSONString());
            tAdd.clear();
            tUpdate.clear();
            tRemove.clear();
            cAdd.clear();
            cUpdate.clear();
            cRemove.clear();
        } else {
            write(resp, emptyBody);
        }
    }

    synchronized void doAction(JSONObject arr, List<LoadPlan> plans, JSONArray add, JSONArray update, JSONArray remove){
        for (LoadPlan plan : plans){
            String id = String.valueOf(plan.getId());
            if (arr.containsKey(id)){
                long hash = (long) arr.remove(id);
                if (plan.hashCode() != hash){
                    update.add(JsonParser.toLogisticJson(plan));
                }
            } else {
                add.add(JsonParser.toLogisticJson(plan));
            }
        }
        for (Object o : arr.keySet()){
            remove.add(Integer.parseInt(String.valueOf(o)));
        }
    }
}
