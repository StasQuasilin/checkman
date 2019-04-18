package api.transport;

import api.IAPI;
import constants.Branches;
import entity.documents.LoadPlan;
import entity.transport.Transportation;
import jdk.internal.org.objectweb.asm.tree.FieldInsnNode;
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
@WebServlet(Branches.API.TRANSPORT_UPDATE)
public class TransportListAPI extends IAPI{
    final HashMap<String, Object> parameters = new HashMap<>();
    {
        parameters.put("transportation/archive", false);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = PostUtil.parseBodyJson(req);
        final JSONObject array = new JSONObject();
        final JSONArray add = new JSONArray();
        final JSONArray update = new JSONArray();
        final JSONArray remove = new JSONArray();

        array.put("add", add);
        array.put("update", update);
        array.put("remove", remove);
        if (body != null) {
            for (LoadPlan loadPlan : hibernator.query(LoadPlan.class, parameters)) {
                String id = String.valueOf(loadPlan.getId());
                if (body.containsKey(id)) {
                    long hash = (long) body.remove(id);
                    if (hash != loadPlan.hashCode()) {
                        update.add(JsonParser.toLogisticJson(loadPlan));
                    }
                } else {
                    add.add(JsonParser.toLogisticJson(loadPlan));
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
