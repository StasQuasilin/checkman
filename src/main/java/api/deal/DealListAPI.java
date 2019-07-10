package api.deal;

import api.API;
import constants.Branches;
import constants.Constants;
import entity.DealType;
import entity.documents.DealHash;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.JsonParser;
import utils.JsonPool;
import utils.UpdateBox;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@WebServlet(Branches.API.DEAL_LIST)
public class DealListAPI extends API {

    final JsonPool pool = JsonPool.getPool();
    final UpdateBox updateBox = UpdateBox.instance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        JSONObject body = parseBody(req);

        if (body != null) {

            LocalDateTime version;
            if (body.containsKey(VERSION)){
                version = LocalDateTime.parse(String.valueOf(body.remove(VERSION)));
            } else {
                version = LocalDateTime.now();
            }
            LocalDateTime value = updateBox.getBoxValue(UpdateBox.BoxType.deal);
            if (version.equals(value)){
                write(resp, emptyBody);
            } else {
                final JSONObject array = pool.getObject();
                final JSONArray add = pool.getArray();
                final JSONArray update = pool.getArray();
                final JSONArray remove = pool.getArray();
                array.put(VERSION, value.toString());
                array.put(ADD, add);
                array.put(UPDATE, update);
                array.put(REMOVE, remove);

                Object o = body.remove(Constants.TYPE);

                if (o != null) {
                    DealType type = DealType.valueOf(String.valueOf(o));

                    for (DealHash deal : dao.getDealHashByType(type)) {
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

                write(resp, add.size() == 0 && update.size() == 0 && remove.size() == 0 ? EMPTY : array.toJSONString());
                pool.put(array);
            }
        } else {
            write(resp, emptyBody);
        }
    }
}
