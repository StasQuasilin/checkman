package api.archive;

import api.API;
import constants.Branches;
import entity.documents.LoadPlan;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.JsonParser;
import utils.PostUtil;
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
 * Created by szpt_user045 on 12.04.2019.
 */
@WebServlet(Branches.API.TRANSPORT_ARCHIVE)
public class TransportArchiveAPI extends API {

    dbDAO dao = dbDAOService.getDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JSONObject array = new JSONObject();
        final JSONArray add = new JSONArray();
        final JSONArray update = new JSONArray();
        final JSONArray remove = new JSONArray();

        array.put("add", add);
        array.put("update", update);
        array.put("remove", remove);

        JSONObject body = PostUtil.parseBodyJson(req);
        if (body != null) {
            for (LoadPlan plan : dao.getTransportArchive()) {
                String id = String.valueOf(plan.getId());
                if (body.containsKey(id)) {
                    long hash = (long) body.remove(id);
                    if (plan.hashCode() != hash) {
                        update.add(JsonParser.toLogisticJson(plan));
                    }
                } else {
                    add.add(JsonParser.toLogisticJson(plan));
                }
            }

            for (Object o : body.keySet()) {
                remove.add(Integer.parseInt(String.valueOf(o)));
            }
        }
        write(resp, array.toJSONString());
    }
}
