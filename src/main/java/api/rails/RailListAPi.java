package api.rails;

import api.API;
import constants.Branches;
import entity.rails.Truck;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.JsonParser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 30.05.2019.
 */
@WebServlet(Branches.API.RAIL_LIST)
public class RailListAPi extends API {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            final JSONObject json = new JSONObject();
            final JSONArray add = new JSONArray();
            final JSONArray update = new JSONArray();
            final JSONArray remove = new JSONArray();
            json.put("add", add);
            json.put("update", update);
            json.put("remove", remove);

            for (Truck truck : hibernator.query(Truck.class, "train/deal/archive", false)){
                String id = String.valueOf(truck.getId());
                if (body.containsKey(id)){
                    long hash = (long) body.remove(id);
                    if (truck.hashCode() != hash){
                        update.add(JsonParser.toJson(truck));
                    }
                } else {
                    add.add(JsonParser.toJson(truck));
                }
//                for (Object o : body.keySet()){
//                    remove.add(Integer.parseInt(String.valueOf(o)));
//                }
            }

            for (Object o : body.keySet()){
                remove.add(Integer.parseInt(String.valueOf(o)));
            }

            write(resp, json.toJSONString());
        } else {
            write(resp, emptyBody);
        }

    }
}
