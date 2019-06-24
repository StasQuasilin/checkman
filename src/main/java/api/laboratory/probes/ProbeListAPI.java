package api.laboratory.probes;

import api.API;
import constants.Branches;
import entity.AnalysesType;
import entity.laboratory.probes.OilProbe;
import entity.laboratory.probes.SunProbe;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.JsonParser;
import utils.PostUtil;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by szpt_user045 on 01.04.2019.
 */
@WebServlet(Branches.API.PROBE_LIST)
public class ProbeListAPI extends API {



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

    final int LIMIT = 20;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            JSONObject items = (JSONObject) body.get("items");

            List<AnalysesType> types = new LinkedList<>();
            if (types.size() == 0) {
                List<SunProbe> sunProbes = dao.getSunProbes();
                for (SunProbe sun : sunProbes) {
                    String id = String.valueOf(sun.getId());
                    if (items.containsKey(id)) {
                        long hash = (long) items.remove(id);
                        if (sun.hashCode() != hash) {
                            update.add(JsonParser.toJson(sun));
                        }
                    } else {
                        add.add(JsonParser.toJson(sun));
                    }
                }
                List<OilProbe> oilProbes = dao.getOilProbes();
                for (OilProbe oil : oilProbes) {
                    String id = String.valueOf(oil.getId());
                    if (items.containsKey(id)) {
                        long hash = (long) items.remove(id);
                        if (oil.hashCode() != hash) {
                            update.add(JsonParser.toJson(oil));
                        }
                    } else {
                        add.add(JsonParser.toJson(oil));
                    }
                }
            }

            for (Object key : items.keySet()) {
                remove.add(Integer.parseInt(String.valueOf(key)));
            }
        }
        write(resp, array.toJSONString());
        add.clear();
        update.clear();
        remove.clear();
    }
}
