package api.laboratory.vro;

import api.API;
import constants.Branches;
import entity.laboratory.subdivisions.vro.VROTurn;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.JsonParser;
import utils.JsonPool;
import utils.hibernate.DateContainers.BETWEEN;
import utils.hibernate.DateContainers.LE;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

/**
 * Created by szpt_user045 on 10.04.2019.
 */
@WebServlet(Branches.API.VRO_LIST)
public class VROListAPI extends API {

    private final JsonPool pool = JsonPool.getPool();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JSONObject array = pool.getObject();
        final JSONArray add = pool.getArray();
        final JSONArray update = pool.getArray();
        final JSONArray remove = pool.getArray();

        array.put(ADD, add);
        array.put(UPDATE, update);
        array.put(REMOVE, remove);

        JSONObject body = parseBody(req);
        if (body != null) {

            List<VROTurn> turns;

            if (body.containsKey("reqDate")){
                String date = String.valueOf(body.remove("reqDate"));
                LocalDate localDate = LocalDate.parse(date);
                turns = dao.getVroTurnsBetween(localDate, localDate.plusDays(1));
            } else {
                turns = dao.getVroTurns();
            }

            for (VROTurn turn : turns) {
                String id = String.valueOf(turn.getId());
                if (body.containsKey(id)) {
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
        }
        write(resp, array.toJSONString());
        pool.put(array);
    }
}
