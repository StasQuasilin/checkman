package api.laboratory.vro;

import api.API;
import constants.Branches;
import entity.laboratory.subdivisions.vro.VROTurn;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.JsonParser;
import utils.PostUtil;
import utils.hibernate.DateContainers.BETWEEN;
import utils.hibernate.DateContainers.LE;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;

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
public class VROListAPI extends API {

    dbDAO dao = dbDAOService.getDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final HashMap<String, Object> parameters = new HashMap<>();
        final JSONObject array = new JSONObject();
        final JSONArray add = new JSONArray();
        final JSONArray update = new JSONArray();
        final JSONArray remove = new JSONArray();

        array.put("add", add);
        array.put("update", update);
        array.put("remove", remove);

        JSONObject body = parseBody(req);
        if (body != null) {
            if (body.containsKey("reqDate")){
                String date = String.valueOf(body.remove("reqDate"));
                LocalDate localDate = LocalDate.parse(date);
                final BETWEEN between = new BETWEEN(Date.valueOf(localDate), Date.valueOf(localDate.plusDays(1)));
                parameters.put("turn/date", between);
            } else {
                final LE le = new LE(Date.valueOf(LocalDate.now()));
                le.setDate(Date.valueOf(LocalDate.now().plusYears(1)));
                parameters.put("turn/date", le);
            }

            for (VROTurn turn : dao.getTurns(parameters)) {
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
        add.clear();
        update.clear();
        remove.clear();
    }
}
