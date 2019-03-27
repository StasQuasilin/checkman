package api.laboratory;

import api.IAPI;
import constants.Branches;
import constants.Constants;
import entity.Worker;
import entity.documents.LoadPlan;
import entity.laboratory.SunAnalyses;
import entity.laboratory.transportation.SunTransportationAnalyses;
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
@WebServlet(Branches.API.LABORATORY_SAVE_SUN)
public class EditSun extends IAPI {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = PostUtil.parseBodyJson(req);
        long planId = (long) body.get(Constants.PLAN);
        LoadPlan loadPlan = hibernator.get(LoadPlan.class, "id", planId);

        HashMap<Long, SunTransportationAnalyses> map = new HashMap<>();

        for (SunTransportationAnalyses analyses : loadPlan.getTransportation().getSunAnalyses()){
            map.put((long) analyses.getId(), analyses);
        }

        for (Object o : (JSONArray)body.get("analyses")){
            JSONObject a = (JSONObject) o;

            SunTransportationAnalyses analyses;
            boolean save = false;
            if (map.containsKey(Constants.ID)){
                long id = (long) a.get(Constants.ID);
                analyses = map.remove(id);
            } else {
                analyses = new SunTransportationAnalyses();
                analyses.setTransportation(loadPlan.getTransportation());
                analyses.setAnalyses(new SunAnalyses());
                save = true;
            }

            SunAnalyses sunAnalyses = analyses.getAnalyses();

            float oiliness = Float.parseFloat(String.valueOf(a.get(Constants.Sun.OILINESS)));
            if (sunAnalyses.getOiliness() != oiliness) {
                sunAnalyses.setOiliness(oiliness);
                save = true;
            }

            float humidity = Float.parseFloat(String.valueOf(a.get(Constants.Sun.HUMIDITY)));
            if (sunAnalyses.getHumidity() != humidity){
                sunAnalyses.setHumidity(humidity);
                save = true;
            }

            float soreness = Float.parseFloat(String.valueOf(a.get(Constants.Sun.SORENESS)));
            if (sunAnalyses.getSoreness() != soreness){
                sunAnalyses.setSoreness(soreness);
                save = true;
            }

            float oilImpurity = Float.parseFloat(String.valueOf(a.get(Constants.Sun.OIL_IMPURITY)));
            if (sunAnalyses.getOilImpurity() != oilImpurity) {
                sunAnalyses.setOilImpurity(oilImpurity);
                save = true;
            }

            float acidValue = Float.parseFloat(String.valueOf(a.get(Constants.Sun.ACID_VALUE)));
            if (sunAnalyses.getAcidValue() != acidValue){
                sunAnalyses.setAcidValue(acidValue);
                save = true;
            }

            if (save){
                ActionTime createTime = sunAnalyses.getCreateTime();
                if (createTime == null) {
                    createTime = new ActionTime();
                    sunAnalyses.setCreateTime(createTime);
                }
                createTime.setTime(new Timestamp(System.currentTimeMillis()));
                Worker worker = getWorker(req);
                createTime.setCreator(worker);
                sunAnalyses.setCreator(worker);

                hibernator.save(sunAnalyses.getCreateTime(), sunAnalyses, analyses);
            }

        }

        for (SunTransportationAnalyses analyses : map.values()){
            hibernator.remove(analyses);
        }

        PostUtil.write(resp, answer);
    }
}
