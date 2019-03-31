package api.laboratory;

import api.IAPI;
import constants.Branches;
import constants.Constants;
import entity.Worker;
import entity.documents.LoadPlan;
import entity.laboratory.OilAnalyses;
import entity.laboratory.transportation.OilTransportationAnalyses;
import entity.transport.ActionTime;
import org.apache.log4j.Logger;
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
public class EditOilAPI extends IAPI {

    private final Logger log = Logger.getLogger(EditOilAPI.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = PostUtil.parseBodyJson(req);
        long planId = (long) body.get(Constants.PLAN);
        log.info("Edit OIL analyses for plan \'" + planId + "\'...");

        LoadPlan loadPlan = hibernator.get(LoadPlan.class, "id", planId);

        HashMap<Long, OilTransportationAnalyses> map = new HashMap<>();
        log.info("\tAlready have analyses:...");
        for (OilTransportationAnalyses analyses : loadPlan.getTransportation().getOilAnalyses()) {
            map.put((long) analyses.getAnalyses().getId(), analyses);
            log.info("\t\t..." + Long.valueOf(analyses.getAnalyses().getId()));
        }

        log.info("\tWill be...");
        for (Object o : (JSONArray) body.get("analyses")) {
            JSONObject a = (JSONObject) o;

            OilTransportationAnalyses analyses;
            boolean save = false;
            
            long id = -1;
            if (a.containsKey(Constants.ID)) {
                id = (long) a.get(Constants.ID);
            }
            
            if (map.containsKey(id)){
                log.info("\t\t...Edit \'" + id + "\'");
                analyses = map.remove(id);
            } else {
                log.info("\t\t...Edit new");
                analyses = new OilTransportationAnalyses();
                analyses.setTransportation(loadPlan.getTransportation());
                analyses.setAnalyses(new OilAnalyses());
                save = true;
            }

            OilAnalyses oilAnalyses = analyses.getAnalyses();

            boolean organoleptic = (boolean) a.get(Constants.Oil.ORGANOLEPTIC);
            log.info("\t\tOrganoleptic: " + organoleptic);
            if (oilAnalyses.isOrganoleptic() != organoleptic){
                oilAnalyses.setOrganoleptic(organoleptic);
                save = true;
            }

//            private int color;
            int color = Integer.parseInt(String.valueOf(a.get(Constants.Oil.COLOR)));
            log.info("\t\tColor: " + color);
            if (oilAnalyses.getColor() != color){
                oilAnalyses.setColor(color);
                save = true;
            }

//            private float acidValue;
            float acidValue = Float.parseFloat(String.valueOf(a.get(Constants.Oil.ACID_VALUE)));
            log.info("\t\tAcid value: " + acidValue);
            if (oilAnalyses.getAcidValue() != acidValue){
                oilAnalyses.setAcidValue(acidValue);
                save = true;
            }

//            private float peroxideValue;
            float peroxideValue = Float.parseFloat(String.valueOf(a.get(Constants.Oil.PEROXIDE_VALUE)));
            log.info("\t\tPeroxide value: " + peroxideValue);
            if (oilAnalyses.getPeroxideValue() != peroxideValue){
                oilAnalyses.setPeroxideValue(peroxideValue);
                save = true;
            }

//            private float phosphorus;
            float phosphorus = Float.parseFloat(String.valueOf(a.get(Constants.Oil.PHOSPHORUS)));
            log.info("\t\tPhosphorus: " + phosphorus);
            if (oilAnalyses.getPhosphorus() != phosphorus) {
                oilAnalyses.setPhosphorus(phosphorus);
                save = true;
            }

//            private float humidity;
            float humidity = Float.parseFloat(String.valueOf(a.get(Constants.Sun.HUMIDITY)));
            log.info("\t\tHumidity: " + humidity);
            if (oilAnalyses.getHumidity() != humidity) {
                oilAnalyses.setHumidity(humidity);
                save = true;
            }

//            private float soap;
            float soap = Float.parseFloat(String.valueOf(a.get(Constants.Oil.SOAP)));
            log.info("\t\tSoap: " + soap);
            if (oilAnalyses.getSoap() != soap) {
                oilAnalyses.setSoap(soap);
                save = true;
            }

//            private float wax;
            float wax = Float.parseFloat(String.valueOf(a.get(Constants.Oil.WAX)));
            log.info("\t\tWax: " + wax);
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
                if (a.containsKey(Constants.CREATOR)){
                    long creatorId = (long) a.get(Constants.CREATOR);
                    log.info("\t\tHave creator");
                    createTime.setCreator(hibernator.get(Worker.class, "id", creatorId));
                } else {
                    log.info("\t\tDoesn't have creator");
                    createTime.setCreator(worker);
                }
                log.info("\t\tCreator: " + createTime.getCreator().getValue());
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
