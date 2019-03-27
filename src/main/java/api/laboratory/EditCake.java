package api.laboratory;

import api.IAPI;
import constants.Branches;
import constants.Constants;
import entity.Worker;
import entity.documents.LoadPlan;
import entity.laboratory.CakeAnalyses;
import entity.laboratory.transportation.CakeTransportationAnalyses;
import entity.transport.ActionTime;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.PostUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;

/**
 * Created by szpt_user045 on 27.03.2019.
 */
@WebServlet(Branches.API.LABORATORY_SAVE_CAKE)
public class EditCake extends IAPI {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = PostUtil.parseBodyJson(req);
        long planId = (long) body.get(Constants.PLAN);
        LoadPlan loadPlan = hibernator.get(LoadPlan.class, "id", planId);

        HashMap<Long, CakeTransportationAnalyses> map = new HashMap<>();

        for (CakeTransportationAnalyses analyses : loadPlan.getTransportation().getCakeAnalyses()) {
            map.put((long) analyses.getId(), analyses);
        }

        for (Object o : (JSONArray) body.get("analyses")) {
            JSONObject a = (JSONObject) o;

            CakeTransportationAnalyses analyses;
            boolean save = false;
            if (map.containsKey(Constants.ID)) {
                long id = (long) a.get(Constants.ID);
                analyses = map.remove(id);
            } else {
                analyses = new CakeTransportationAnalyses();
                analyses.setTransportation(loadPlan.getTransportation());
                analyses.setAnalyses(new CakeAnalyses());
                save = true;
            }

            CakeAnalyses cakeAnalyses = analyses.getAnalyses();

//            private float humidity;
            float humidity = Float.parseFloat(String.valueOf(a.get(Constants.Sun.HUMIDITY)));
            if (cakeAnalyses.getHumidity() != humidity) {
                cakeAnalyses.setHumidity(humidity);
                save = true;
            }
            
//            private float protein;
            float protein = Float.parseFloat(String.valueOf(a.get(Constants.Cake.PROTEIN)));
            if (cakeAnalyses.getProtein() != protein) {
                cakeAnalyses.setProtein(protein);
                save = true;
            }

//            private float cellulose;
            float cellulose = Float.parseFloat(String.valueOf(a.get(Constants.Cake.CELLULOSE)));
            if (cakeAnalyses.getCellulose() != cellulose) {
                cakeAnalyses.setCellulose(cellulose);
                save = true;
            }
            
//            private float oiliness;
            float oiliness = Float.parseFloat(String.valueOf(a.get(Constants.Sun.OILINESS)));
            if (cakeAnalyses.getOiliness() != oiliness) {
                cakeAnalyses.setOiliness(oiliness);
                save = true;
            }

            if (save){
                ActionTime createTime = cakeAnalyses.getCreateTime();
                if (createTime == null) {
                    createTime = new ActionTime();
                    cakeAnalyses.setCreateTime(createTime);
                }
                createTime.setTime(new Timestamp(System.currentTimeMillis()));
                Worker worker = getWorker(req);
                createTime.setCreator(worker);
                cakeAnalyses.setCreator(worker);

                hibernator.save(cakeAnalyses.getCreateTime(), cakeAnalyses, analyses);
            }
        }

        for (CakeTransportationAnalyses analyses : map.values()){
            hibernator.remove(analyses);
        }

        PostUtil.write(resp, answer);
    }
}
