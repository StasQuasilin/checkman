package api.laboratory;

import api.IAPI;
import bot.BotFactory;
import bot.Notificator;
import constants.Branches;
import constants.Constants;
import entity.Worker;
import entity.documents.LoadPlan;
import entity.laboratory.CakeAnalyses;
import entity.laboratory.transportation.CakeTransportationAnalyses;
import entity.transport.ActionTime;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by szpt_user045 on 27.03.2019.
 */
@WebServlet(Branches.API.LABORATORY_SAVE_CAKE)
public class EditCakeAPI extends IAPI {
    
    private final Logger log = Logger.getLogger(EditCakeAPI.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            long planId = (long) body.get(Constants.PLAN);
            log.info("Edit CAKE analyses for plan \'" + planId + "\'...");

            LoadPlan loadPlan = hibernator.get(LoadPlan.class, "id", planId);

            HashMap<Long, CakeTransportationAnalyses> map = new HashMap<>();
            log.info("\tAlready have analyses:...");
            for (CakeTransportationAnalyses analyses : loadPlan.getTransportation().getCakeAnalyses()) {
                map.put((long) analyses.getAnalyses().getId(), analyses);
                log.info("\t\t..." + Long.valueOf(analyses.getAnalyses().getId()));
            }
            List<CakeAnalyses> analysesList = new LinkedList<>();
            log.info("\tWill be...");
            for (Object o : (JSONArray) body.get("analyses")) {
                JSONObject a = (JSONObject) o;

                CakeTransportationAnalyses analyses;
                boolean save = false;

                long id = -1;
                if (a.containsKey(Constants.ID)) {
                    id = (long) a.get(Constants.ID);
                }
                if (map.containsKey(id)) {
                    log.info("\t\t...Edit \'" + id + "\'");
                    analyses = map.remove(id);
                } else {
                    log.info("\t\t...Edit new");
                    analyses = new CakeTransportationAnalyses();
                    analyses.setTransportation(loadPlan.getTransportation());
                    analyses.setAnalyses(new CakeAnalyses());
                    save = true;
                }

                CakeAnalyses cakeAnalyses = analyses.getAnalyses();

//            private float humidity;
                float humidity = Float.parseFloat(String.valueOf(a.get(Constants.Sun.HUMIDITY_1)));
                log.info("\t\tHumidity: " + humidity);
                if (cakeAnalyses.getHumidity() != humidity) {
                    cakeAnalyses.setHumidity(humidity);
                    save = true;
                }

//            private float protein;
                float protein = Float.parseFloat(String.valueOf(a.get(Constants.Cake.PROTEIN)));
                log.info("\t\tProtein: " + protein);
                if (cakeAnalyses.getProtein() != protein) {
                    cakeAnalyses.setProtein(protein);
                    save = true;
                }

//            private float cellulose;
                float cellulose = Float.parseFloat(String.valueOf(a.get(Constants.Cake.CELLULOSE)));
                log.info("\t\tCellulose: " + cellulose);
                if (cakeAnalyses.getCellulose() != cellulose) {
                    cakeAnalyses.setCellulose(cellulose);
                    save = true;
                }

//            private float oiliness;
                float oiliness = Float.parseFloat(String.valueOf(a.get(Constants.Sun.OILINESS)));
                log.info("\t\tOiliness: " + oiliness);
                if (cakeAnalyses.getOiliness() != oiliness) {
                    cakeAnalyses.setOiliness(oiliness);
                    save = true;
                }

                if (save) {
                    ActionTime createTime = cakeAnalyses.getCreateTime();
                    if (createTime == null) {
                        createTime = new ActionTime();
                        cakeAnalyses.setCreateTime(createTime);
                    }
                    createTime.setTime(new Timestamp(System.currentTimeMillis()));
                    Worker worker = getWorker(req);
                    if (a.containsKey(Constants.CREATOR)) {
                        long creatorId = (long) a.get(Constants.CREATOR);
                        log.info("\t\tHave creator");
                        createTime.setCreator(hibernator.get(Worker.class, "id", creatorId));
                    } else {
                        log.info("\t\tDoesn't have creator");
                        createTime.setCreator(worker);
                    }
                    log.info("\t\tCreator: " + createTime.getCreator().getValue());
                    cakeAnalyses.setCreator(worker);

                    hibernator.save(cakeAnalyses.getCreateTime(), cakeAnalyses, analyses);
                    analysesList.add(cakeAnalyses);
                }
            }

            Notificator notificator = BotFactory.getNotificator();
            if (notificator != null) {
                notificator.cakeAnalysesShow(loadPlan, analysesList);
            }

            for (CakeTransportationAnalyses analyses : map.values()) {
                hibernator.remove(analyses);
            }

            write(resp, answer);
        } else {
            write(resp, emptyBody);
        }
    }
}
