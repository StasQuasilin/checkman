package api.laboratory;

import api.ServletAPI;
import bot.BotFactory;
import bot.Notificator;
import constants.Branches;
import constants.Constants;
import entity.Worker;
import entity.documents.LoadPlan;
import entity.laboratory.MealAnalyses;
import entity.transport.ActionTime;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;

/**
 * Created by szpt_user045 on 27.03.2019.
 */
@WebServlet(Branches.API.LABORATORY_SAVE_CAKE)
public class EditCakeServletAPI extends ServletAPI {
    
    private final Logger log = Logger.getLogger(EditCakeServletAPI.class);


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            long planId = (long) body.get(Constants.PLAN);
            log.info("Edit CAKE analyses for plan \'" + planId + "\'...");

            LoadPlan loadPlan = dao.getLoadPlanById(planId);
            MealAnalyses mealAnalyses = loadPlan.getTransportation().getMealAnalyses();
            if (mealAnalyses == null) {
                mealAnalyses = new MealAnalyses();
                loadPlan.getTransportation().setMealAnalyses(mealAnalyses);
            }

            JSONObject a = (JSONObject) body.get("analyses");
            boolean save = false;
            float humidity = Float.parseFloat(String.valueOf(a.get(Constants.Oil.HUMIDITY)));
            log.info("\t\tHumidity: " + humidity);
            if (mealAnalyses.getHumidity() != humidity) {
                mealAnalyses.setHumidity(humidity);
                save = true;
            }

            float protein = Float.parseFloat(String.valueOf(a.get(Constants.Cake.PROTEIN)));
            log.info("\t\tProtein: " + protein);
            if (mealAnalyses.getProtein() != protein) {
                mealAnalyses.setProtein(protein);
                save = true;
            }

            float cellulose = Float.parseFloat(String.valueOf(a.get(Constants.Cake.CELLULOSE)));
            log.info("\t\tCellulose: " + cellulose);
            if (mealAnalyses.getCellulose() != cellulose) {
                mealAnalyses.setCellulose(cellulose);
                save = true;
            }

            float oiliness = Float.parseFloat(String.valueOf(a.get(Constants.Sun.OILINESS)));
            log.info("\t\tOiliness: " + oiliness);
            if (mealAnalyses.getOiliness() != oiliness) {
                mealAnalyses.setOiliness(oiliness);
                save = true;
            }

            if (save) {
                ActionTime createTime = mealAnalyses.getCreateTime();
                if (createTime == null) {
                    createTime = new ActionTime();
                    mealAnalyses.setCreateTime(createTime);
                }
                createTime.setTime(new Timestamp(System.currentTimeMillis()));
                Worker worker = getWorker(req);
                if (a.containsKey(Constants.CREATOR)) {
                    long creatorId = (long) a.get(Constants.CREATOR);
                    log.info("\t\tHave creator");
                    createTime.setCreator(dao.getWorkerById(creatorId));
                } else {
                    log.info("\t\tDoesn't have creator");
                    createTime.setCreator(worker);
                }
                mealAnalyses.setCreator(worker);

                dao.saveCakeAnalyses(mealAnalyses);
                dao.saveTransportation(loadPlan.getTransportation());

                Notificator notificator = BotFactory.getNotificator();
                if (notificator != null) {
                    notificator.cakeAnalysesShow(loadPlan, mealAnalyses);
                }
            }

            write(resp, SUCCESS_ANSWER);
        } else {
            write(resp, emptyBody);
        }
    }
}