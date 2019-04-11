package api.laboratory.vro;

import api.IAPI;
import constants.Branches;
import entity.laboratory.subdivisions.vro.VROTurn;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.JsonParser;
import utils.PostUtil;
import utils.hibernate.DateContainers.LE;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;

/**
 * Created by szpt_user045 on 10.04.2019.
 */
@WebServlet(Branches.API.VRO_LIST)
public class VROListAPI extends IAPI {

    final JSONObject array = new JSONObject();
    final JSONArray add = new JSONArray();
    final JSONArray update = new JSONArray();
    final JSONArray remove = new JSONArray();

    {
        array.put("add", add);
        array.put("update", update);
        array.put("delete", remove);
    }

    final HashMap<String, Object> parameters = new HashMap<>();
    final LE le = new LE(Date.valueOf(LocalDate.now()));

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        JSONObject body = PostUtil.parseBodyJson(req);
        le.setDate(Date.valueOf(LocalDate.now().plusDays(1)));
        parameters.put("date", le);

        for (VROTurn turn : hibernator.limitQuery(VROTurn.class, parameters, 14)){
            String id = String.valueOf(turn.getId());
            if (body.containsKey(id)){
                long hash = (long) body.remove(id);
                if (turn.hashCode() != hash) {
                    update.add(JsonParser.VRO.toJson(turn));
                }
            } else {
                add.add(JsonParser.VRO.toJson(turn));
            }
        }

        for (Object o : body.keySet()) {
            remove.add(Integer.parseInt(String.valueOf(o)));
        }
        write(resp, array.toJSONString());
        add.clear();
        update.clear();
        remove.clear();
    }
}