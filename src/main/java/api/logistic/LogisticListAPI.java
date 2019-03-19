package api.logistic;

import api.IAPI;
import constants.Branches;
import entity.documents.LoadPlan;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.ApplicationSettingsBox;
import utils.PostUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by quasilin on 18.03.2019.
 */
@WebServlet(Branches.API.LOGISTIC_LIST)
public class LogisticListAPI extends IAPI {

    final ApplicationSettingsBox settingsBox = ApplicationSettingsBox.getBox();

    final JSONObject array = new JSONObject();
    final JSONArray add = new JSONArray();
    final JSONArray update = new JSONArray();
    final JSONArray delete = new JSONArray();
    final HashMap<String, Object> parameters = new HashMap<>();

    {
        parameters.put("customer", settingsBox.getSettings().getCustomer());
        parameters.put("transportation/archive", false);
        array.put("add", add);
        array.put("update", update);
        array.put("delete", delete);
    }



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HashMap<String, String> body = PostUtil.parseBody(req);
        List<LoadPlan> loadPlans = hibernator.query(LoadPlan.class, parameters);
        for (LoadPlan loadPlan : loadPlans){
            String id = String.valueOf(loadPlan.getTransportation().getId());
            if (body.containsKey(id)){
                int hash = Integer.parseInt(body.remove(id));
                if (hash != loadPlan.getTransportation().hashCode()){
                    update.add(loadPlan.getTransportation());
                }
            }
        }
    }
}
