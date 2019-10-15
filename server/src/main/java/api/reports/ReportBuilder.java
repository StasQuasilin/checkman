package api.reports;

import api.ServletAPI;
import constants.Branches;
import entity.production.TurnSettings;
import entity.transport.Transportation;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;
import utils.turns.TurnBox;
import utils.turns.TurnService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by szpt_user045 on 15.10.2019.
 */
@WebServlet(Branches.API.REPORT_BUILDER)
public class ReportBuilder extends ServletAPI {

    private static final long serialVersionUID = 1614658093016524992L;


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){

            JSONObject report = pool.getObject();
            HashMap<String, Object> params = new HashMap<>();

            Date date = Date.valueOf(String.valueOf(body.get(DATE)));
            report.put("date", date.toString());
            params.put("date", date);

            int turnNumber = Integer.parseInt(String.valueOf(body.get(TURN)));
            if (turnNumber != -1) {
                TurnSettings turn = TurnBox.getTurnByNumber(turnNumber);

            }

            int productId = Integer.parseInt(String.valueOf(body.get(PRODUCT)));
            if (productId != -1){
                params.put("product", productId);
            }
            int storageId = Integer.parseInt(String.valueOf(body.get(STORAGE)));

            JSONObject loads = pool.getObject();
            for (Transportation transportation : dao.getObjectsByParams(Transportation.class, params)){
                String product = transportation.getProduct().getName();
                if (!loads.containsKey(product)){
                    JSONObject j = pool.getObject();
                    j.put("weight", 0);
                    j.put("values", pool.getArray());
                    loads.put(product, j);
                }
                JSONObject o = (JSONObject) loads.get(product);
                float weight = Float.parseFloat(String.valueOf(o.get("weight")));
                weight += transportation.getWeight().getNetto();
                o.put("weight", weight);
                JSONArray a = (JSONArray) o.get("values");
                a.add(parser.toJson(transportation));
            }

            report.put("loads", loads);
            write(resp, report.toJSONString());
            pool.put(report);

        }
    }
}
