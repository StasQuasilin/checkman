package api.laboratory;

import api.IAPI;
import constants.Branches;
import constants.Constants;
import entity.Worker;
import entity.documents.LoadPlan;
import entity.laboratory.OilAnalyses;
import entity.laboratory.transportation.OilTransportationAnalyses;
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
@WebServlet(Branches.API.LABORATORY_SAVE_OIL)
public class EditOil extends IAPI {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = PostUtil.parseBodyJson(req);
        long planId = (long) body.get(Constants.PLAN);
        LoadPlan loadPlan = hibernator.get(LoadPlan.class, "id", planId);

        HashMap<Long, OilTransportationAnalyses> map = new HashMap<>();

        for (OilTransportationAnalyses analyses : loadPlan.getTransportation().getOilAnalyses()) {
            map.put((long) analyses.getId(), analyses);
        }

        for (Object o : (JSONArray) body.get("analyses")) {
            JSONObject a = (JSONObject) o;

            OilTransportationAnalyses analyses;
            boolean save = false;
            if (map.containsKey(Constants.ID)) {
                long id = (long) a.get(Constants.ID);
                analyses = map.remove(id);
            } else {
                analyses = new OilTransportationAnalyses();
                analyses.setTransportation(loadPlan.getTransportation());
                analyses.setAnalyses(new OilAnalyses());
                save = true;
            }

            OilAnalyses oilAnalyses = analyses.getAnalyses();

//            private boolean organoleptic;
            boolean organoleptic = (boolean) a.get(Constants.Oil.ORGANOLEPTIC);
            if (oilAnalyses.isOrganoleptic() != organoleptic){
                oilAnalyses.setOrganoleptic(organoleptic);
                save = true;
            }

//            private int color;
            int color = Integer.parseInt(String.valueOf(a.get(Constants.Oil.COLOR)));
            if (oilAnalyses.getColor() != color){
                oilAnalyses.setColor(color);
                save = true;
            }

//            private float acidValue;
            float acidValue = Float.parseFloat(String.valueOf(a.get(Constants.Oil.ACID_VALUE)));
            if (oilAnalyses.getAcidValue() != acidValue){
                oilAnalyses.setAcidValue(acidValue);
                save = true;
            }

//            private float peroxideValue;
            float peroxideValue = Float.parseFloat(String.valueOf(a.get(Constants.Oil.PEROXIDE_VALUE)));
            if (oilAnalyses.getPeroxideValue() != peroxideValue){
                oilAnalyses.setPeroxideValue(peroxideValue);
                save = true;
            }

//            private float phosphorus;
            float phosphorus = Float.parseFloat(String.valueOf(a.get(Constants.Oil.PHOSPHORUS)));
            if (oilAnalyses.getPhosphorus() != phosphorus) {
                oilAnalyses.setPhosphorus(phosphorus);
                save = true;
            }

//            private float humidity;
            float humidity = Float.parseFloat(String.valueOf(a.get(Constants.Sun.HUMIDITY)));
            if (oilAnalyses.getHumidity() != humidity) {
                oilAnalyses.setHumidity(humidity);
                save = true;
            }

//            private float soap;
            float soap = Float.parseFloat(String.valueOf(a.get(Constants.Oil.SOAP)));
            if (oilAnalyses.getSoap() != soap) {
                oilAnalyses.setSoap(soap);
                save = true;
            }

//            private float wax;
            float wax = Float.parseFloat(String.valueOf(a.get(Constants.Oil.WAX)));
            if (oilAnalyses.getWax() != wax) {
                oilAnalyses.setWax(wax);
                save = true;
            }

            if (save){
                ActionTime createTime = oilAnalyses.getCreateTime();
                if (createTime == null) {
                    createTime = new ActionTime();
                    oilAnalyses.setCreateTime(createTime);
                }
                createTime.setTime(new Timestamp(System.currentTimeMillis()));
                Worker worker = getWorker(req);
                createTime.setCreator(worker);
                oilAnalyses.setCreator(worker);

                hibernator.save(oilAnalyses.getCreateTime(), oilAnalyses, analyses);
            }
        }

        for (OilTransportationAnalyses analyses : map.values()){
            hibernator.remove(analyses);
        }

        PostUtil.write(resp, answer);
    }
}
