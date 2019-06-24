package api.deal;

import api.API;
import constants.Branches;
import constants.Constants;
import entity.DealType;
import entity.documents.Deal;
import entity.documents.DealHash;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.JsonParser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@WebServlet(Branches.API.DEAL_LIST)
public class DealListAPI extends API {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JSONObject array = new JSONObject();
        final JSONArray add = new JSONArray();
        final JSONArray update = new JSONArray();
        final JSONArray remove = new JSONArray();
        array.put("add", add);
        array.put("update", update);
        array.put("remove", remove);

        JSONObject body = parseBody(req);

        if (body != null) {
            for (DealHash deal : dao.getDealHashByType(DealType.valueOf(req.getParameter(Constants.TYPE)))) {
                String id = String.valueOf(deal.getId());
                if (body.containsKey(id)) {
                    long hash = (long) body.remove(id);
                    if (hash != deal.hashCode()) {
                        update.add(JsonParser.toJson(dao.getDealById(deal.getId())));
                    }
                } else {
                    add.add(JsonParser.toJson(dao.getDealById(deal.getId())));
                }
            }

            for (Object key : body.keySet()) {
                remove.add(Integer.parseInt((String) key));
            }
        }
        write(resp, array.toJSONString());
        add.clear();
        update.clear();
        remove.clear();
    }
}
