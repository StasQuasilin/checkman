package api.laboratory;

import api.API;
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
public class EditCakeAPI extends API {
    
    private final Logger log = Logger.getLogger(EditCakeAPI.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            long planId = (long) body.get(Constants.PLAN);
            log.info("Edit CAKE analyses for plan \'" + planId + "\'...");

            LoadPlan loadPlan = hibernator.get(LoadPlan.class, "id", planId);
            CakeAnalyses cakeAnalyses = loadPlan.getTransportation().getCakeAnalyses();
            if (cakeAnalyses == null) {
                cakeAnalyses = new CakeAnalyses();
                loadPlan.getTransportation().setCakeAnalyses(cakeAnalyses);
            }

            boolean save = false;
            float humidity = Float.parseFloat(String.valueOf(body.get(Constants.Oil.HUMIDITY)));
            log.info("\t\tHumidity: " + humidity);
            if (cakeAnalyses.getHumidity() != humidity) {
                cakeAnalyses.setHumidity(humidity);
                save = true;
            }

            float protein = Float.parseFloat(String.valueOf(body.get(Constants.Cake.PROTEIN)));
            log.info("\t\tProtein: " + protein);
            if (cakeAnalyses.getProtein() != protein) {
                cakeAnalyses.setProtein(protein);
                save = true;
            }

            float cellulose = Float.parseFloat(String.valueOf(body.get(Constants.Cake.CELLULOSE)));
            log.info("\t\tCellulose: " + cellulose);
            if (cakeAnalyses.getCellulose() != cellulose) {
                cakeAnalyses.setCellulose(cellulose);
                save = true;
            }

            float oiliness = Float.parseFloat(String.valueOf(body.get(Constants.Sun.OILINESS)));
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
                if (body.containsKey(Constants.CREATOR)) {
                    long creatorId = (long) body.get(Constants.CREATOR);
                    log.info("\t\tHave creator");
                    createTime.setCreator(hibernator.get(Worker.class, "id", creatorId));
                } else {
                    log.info("\t\tDoesn't have creator");
                    createTime.setCreator(worker);
                }
                cakeAnalyses.setCreator(worker);

                hibernator.save(cakeAnalyses.getCreateTime(), cakeAnalyses);

                Notificator notificator = BotFactory.getNotificator();
                if (notificator != null) {
                    notificator.cakeAnalysesShow(loadPlan, cakeAnalyses);
                }
            }

            write(resp, answer);
        } else {
            write(resp, emptyBody);
        }
    }
}
