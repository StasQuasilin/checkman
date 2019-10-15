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

            Date date = Date.valueOf(String.valueOf(body.get(DATE)));
            report.put("date", date.toString());

            int turnNumber = Integer.parseInt(String.valueOf(body.get(TURN)));
            if (turnNumber != -1) {
                TurnSettings turn = TurnBox.getTurnByNumber(turnNumber);

            }

            int productId = Integer.parseInt(String.valueOf(body.get(PRODUCT)));
            int storageId = Integer.parseInt(String.valueOf(body.get(STORAGE)));



            HashMap<String, Object> params = new HashMap<>();

            params.put("date", date);
            JSONArray loads = pool.getArray();
            for (Transportation transportation : dao.getObjectsByParams(Transportation.class, params)){
                loads.add(parser.toJson(transportation));
            }

            report.put("loads", loads);
            write(resp, report.toJSONString());
            pool.put(report);

        }
    }
}
