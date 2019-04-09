package api.laboratory.extraction;

import api.IAPI;
import constants.Branches;
import entity.laboratory.subdivisions.extraction.ExtractionTurn;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;
import utils.JsonParser;
import utils.PostUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by szpt_user045 on 09.04.2019.
 */
@WebServlet(Branches.API.EXTRACTION_LIST)
public class ExtractionListAPI extends IAPI {
    final JSONObject array = new JSONObject();
    final JSONArray add = new JSONArray();
    final JSONArray update = new JSONArray();
    final JSONArray remove = new JSONArray();
    final HashMap<String, Object> parameters = new HashMap<>();

    {
        array.put("add", add);
        array.put("update", update);
        array.put("delete", remove);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = PostUtil.parseBodyJson(req);

        for (ExtractionTurn turn : hibernator.query(ExtractionTurn.class, parameters)){
            String id = String.valueOf(turn.getId());
            if (body.containsKey(id)){
                long hash = (long) body.remove(id);
                if (turn.hashCode() != hash){
                    update.add(JsonParser.Laboratory.Extraction.toJson(turn));
                }
            } else  {
                add.add(JsonParser.Laboratory.Extraction.toJson(turn));
            }
        }

        for (Object o : body.keySet()){
            remove.add(Integer.parseInt(String.valueOf(o)));
        }

        write(resp, array.toJSONString());
        add.clear();
        update.clear();
        remove.clear();
    }
}
