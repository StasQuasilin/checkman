package api.summary;

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
import java.util.HashMap;
import java.util.List;

/**
 * Created by quasilin on 01.04.2019.
 */
@WebServlet(Branches.ShortCuts.UPDATE)
public class ShortCutServletAPI extends ServletAPI {
    
    final JsonPool pool = JsonPool.getPool();

    final HashMap<String, Object> parameters = new HashMap<>();
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        final JSONObject array = pool.getObject();
//        final JSONObject territory = pool.getObject();
//        final JSONObject cruise = pool.getObject();
//        final JSONArray tAdd = pool.getArray();
//        final JSONArray tUpdate = pool.getArray();
//        final JSONArray tRemove = pool.getArray();
//        final JSONArray cAdd = pool.getArray();
//        final JSONArray cUpdate = pool.getArray();
//        final JSONArray cRemove = pool.getArray();
//        {
//            array.put("territory", territory);
//            territory.put("add", tAdd);
//            territory.put("update", tUpdate);
//            territory.put("remove", tRemove);
//            array.put("cruise", cruise);
//            cruise.put("add", cAdd);
//            cruise.put("update", cUpdate);
//            cruise.put("remove", cRemove);
//        }
//
//        JSONObject body = parseBody(req);
//
//        if (body != null) {
////            parameters.clear();
////            parameters.put("transportation/timeIn", State.notNull);
////            parameters.put("transportation/archive", false);
////            doAction((JSONObject) body.get("territory"), dao.getTransportationsOnTerritory(), tAdd, tUpdate, tRemove);
////
////            parameters.clear();
////            parameters.put("transportation/timeOut", State.notNull);
////            parameters.put("transportation/archive", false);
////            doAction((JSONObject) body.get("cruise"), dao.getTransportationsOnCruise(), cAdd, cUpdate, cRemove);
//
//            write(resp, array.toJSONString());
//            tAdd.clear();
//            tUpdate.clear();
//            tRemove.clear();
//            cAdd.clear();
//            cUpdate.clear();
//            cRemove.clear();
//        } else {
//            write(resp, emptyBody);
//        }
        write(resp, "[]");
    }

    synchronized void doAction(JSONObject arr, List<LoadPlan> plans, JSONArray add, JSONArray update, JSONArray remove){
        String id;
        for (LoadPlan plan : plans){
            id = String.valueOf(plan.getId());
            if (arr.containsKey(id)){
                long hash = (long) arr.remove(id);
                if (plan.hashCode() != hash){
                    update.add(parser.toLogisticJson(plan));
                }
            } else {
                add.add(parser.toLogisticJson(plan));
            }
        }
        for (Object o : arr.keySet()){
            remove.add(Integer.parseInt(String.valueOf(o)));
        }
    }
}
