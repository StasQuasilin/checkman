package api.transport;

import api.API;
import constants.Branches;
import constants.Constants;
import entity.documents.LoadPlan;
import entity.transport.TransportationNote;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.JsonParser;
import utils.JsonPool;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by szpt_user045 on 02.07.2019.
 */
@WebServlet(Branches.API.TRANSPORT_NOTES_LIST)
public class NoteListAPI extends API {

    final JsonPool pool = JsonPool.getPool();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        final JSONObject arr = pool.getObject();
        final JSONArray add = pool.getArray();
        final JSONArray upd = pool.getArray();
        final JSONArray rem = pool.getArray();
        arr.put(ADD, add);
        arr.put(UPDATE, upd);
        arr.put(REMOVE, rem);
        if (body != null) {
            Object planId = body.get(Constants.PLAN);
            LoadPlan plan = dao.getLoadPlanById(planId);
            JSONObject notes = (JSONObject) body.get("notes");
            String id;
            for (TransportationNote note : dao.getTransportationNotesByTransportation(plan.getTransportation())){
                id = String.valueOf(note.getId());
                if (notes.containsKey(id)){
                    String text = (String) notes.remove(id);
                    if (!text.equals(note.getNote())){
                        upd.add(JsonParser.toJson(note));
                    }
                } else {
                    add.add(JsonParser.toJson(note));
                }
            }
            rem.addAll((Collection) notes.keySet().stream().map(o -> Integer.parseInt(o.toString())).collect(Collectors.toList()));
            write(resp, arr.toJSONString());
            pool.put(arr);
        }
    }
}
