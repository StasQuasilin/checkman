package api.laboratory.probes;

import api.API;
import constants.Branches;
import entity.AnalysesType;
import entity.laboratory.probes.OilProbe;
import entity.laboratory.probes.SunProbe;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.JsonParser;
import utils.JsonPool;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by szpt_user045 on 01.04.2019.
 */
@WebServlet(Branches.API.PROBE_LIST)
public class ProbeListAPI extends API {

    final JsonPool pool = JsonPool.getPool();
    public static final String ITEMS = "items";
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        final JSONObject array = pool.getObject();
        final JSONArray add = pool.getArray();
        final JSONArray update = pool.getArray();
        final JSONArray remove = pool.getArray();
        {
            array.put(ADD, add);
            array.put(UPDATE, update);
            array.put(REMOVE, remove);
        }
        if (body != null) {
            JSONObject items = (JSONObject) body.get(ITEMS);

            List<SunProbe> sunProbes = dao.getLimitSunProbes(null);
            String id;
            for (SunProbe sun : sunProbes) {
                id = "" + (sun.getId());
                if (items.containsKey(id)) {
                    long hash = (long) items.remove(id);
                    if (sun.hashCode() != hash) {
                        update.add(JsonParser.toJson(sun));
                    }
                } else {
                    add.add(JsonParser.toJson(sun));
                }
            }
            List<OilProbe> oilProbes = dao.getLimitOilProbes(null);
            for (OilProbe oil : oilProbes) {
                id = "" + (oil.getId());
                if (items.containsKey(id)) {
                    long hash = (long) items.remove(id);
                    if (oil.hashCode() != hash) {
                        update.add(JsonParser.toJson(oil));
                    }
                } else {
                    add.add(JsonParser.toJson(oil));
                }
            }

            for (Object key : items.keySet()) {
                remove.add(Integer.parseInt(String.valueOf(key)));
            }
        }
        write(resp, array.toJSONString());
        pool.put(array);
    }
}
