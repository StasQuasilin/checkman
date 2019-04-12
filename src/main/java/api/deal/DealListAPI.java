package api.deal;

import api.IAPI;
import constants.Branches;
import constants.Constants;
import entity.DealType;
import entity.documents.Deal;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.JsonParser;
import utils.PostUtil;
import utils.U;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@WebServlet(Branches.API.DEAL_LIST)
public class DealListAPI extends IAPI{

    final JSONObject array = new JSONObject();
    final JSONArray add = new JSONArray();
    final JSONArray update = new JSONArray();
    final JSONArray remove = new JSONArray();
    final static HashMap<String, Object> parameters = new HashMap<>();
    {
        parameters.put("archive", false);
        array.put("add", add);
        array.put("update", update);
        array.put("remove", remove);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        update(req, resp);
    }
    synchronized void update(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        parameters.put(Constants.TYPE, DealType.valueOf(req.getParameter(Constants.TYPE)));
        JSONObject body = PostUtil.parseBodyJson(req);

        List<Deal> query = hibernator.query(Deal.class, parameters);
        for (Deal deal : query){
            String id = String.valueOf(deal.getId());
            if (body.containsKey(id)){
                long hash = (long) body.remove(id);
                if (hash != deal.hashCode()){
                    update.add(JsonParser.toJson(deal));
                }
            } else {
                add.add(JsonParser.toJson(deal));
            }

        }

        for (Object key : body.keySet()){
            remove.add(Integer.parseInt((String) key));
        }

        write(resp, array.toJSONString());
        add.clear();
        update.clear();
        remove.clear();
//        query.clear();
    }
}
