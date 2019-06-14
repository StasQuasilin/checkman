package api.laboratory.kpo;

import api.API;
import constants.Branches;
import entity.laboratory.subdivisions.kpo.KPOPart;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.JsonParser;
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
 * Created by szpt_user045 on 22.05.2019.
 */
@WebServlet(Branches.API.KPO_PART_LIST)
public class KPOPartListAPI extends API {


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            final HashMap<String, Object> parameters = new HashMap<>();
            final LE le = new LE(Date.valueOf(LocalDate.now()));

            final JSONObject array = new JSONObject();
            final JSONArray add = new JSONArray();
            final JSONArray update = new JSONArray();
            final JSONArray remove = new JSONArray();
            array.put("add", add);
            array.put("update", update);
            array.put("remove", remove);

            le.setDate(Date.valueOf(LocalDate.now().plusYears(1)));
            parameters.put("date", le);
            for (KPOPart part : hibernator.limitQuery(KPOPart.class, parameters, 14)){
                String id = String.valueOf(part.getId());
                if (body.containsKey(id)){
                    long hash = (long) body.remove(id);
                    if (part.hashCode() != hash){
                        update.add(JsonParser.toJson(part));
                    }
                } else {
                    add.add(JsonParser.toJson(part));
                }
            }
            for (Object o : body.keySet()){
                remove.add(Integer.parseInt(String.valueOf(o)));
            }

            write(resp, array.toJSONString());
        } else {
            write(resp, emptyBody);
        }
    }
}
