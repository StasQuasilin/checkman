package api.laboratory;

import api.API;
import bot.BotFactory;
import bot.Notificator;
import constants.Branches;
import constants.Constants;
import entity.Worker;
import entity.documents.LoadPlan;
import entity.laboratory.SunAnalyses;
import entity.laboratory.transportation.SunTransportationAnalyses;
import entity.transport.ActionTime;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.TransportUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by szpt_user045 on 27.03.2019.
 */
@WebServlet(Branches.API.LABORATORY_SAVE_SUN)
public class EditSunAPI extends API {

    private final Logger log = Logger.getLogger(EditSunAPI.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            long planId = (long) body.get(Constants.PLAN);
            log.info("Edit SUN analyses for plan \'" + planId + "\'...");

            final LoadPlan loadPlan = hibernator.get(LoadPlan.class, "id", planId);
            final LinkedList<SunAnalyses> analysesList = new LinkedList<>();
            final HashMap<Long, SunTransportationAnalyses> map = new HashMap<>();
            log.info("\tAlready have analyses:...");
            for (SunTransportationAnalyses analyses : loadPlan.getTransportation().getSunAnalyses()) {
                map.put((long) analyses.getAnalyses().getId(), analyses);
                log.info("\t\t..." + Long.valueOf(analyses.getAnalyses().getId()));
            }
            Worker worker = getWorker(req);
            Worker creator;
            if (body.containsKey(Constants.CREATOR)) {
                long creatorId = (long) body.get(Constants.CREATOR);
                log.info("\t\tHave creator");
                creator = hibernator.get(Worker.class, "id", creatorId);
            } else {
                log.info("\t\tDoesn't have creator");
                creator = worker;
            }
            log.info("\tWill be...");
            for (Object o : (JSONArray) body.get("analyses")) {
                JSONObject a = (JSONObject) o;

                SunTransportationAnalyses analyses;
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
                    analyses = new SunTransportationAnalyses();
                    analyses.setTransportation(loadPlan.getTransportation());
                    analyses.setAnalyses(new SunAnalyses());
                    save = true;
                }

                SunAnalyses sunAnalyses = analyses.getAnalyses();
                analysesList.add(sunAnalyses);

                float oiliness = Float.parseFloat(String.valueOf(a.get(Constants.Sun.OILINESS)));
                log.info("\t\tOiliness: " + oiliness);
                if (sunAnalyses.getOiliness() != oiliness) {
                    sunAnalyses.setOiliness(oiliness);
                    save = true;
                }

                float humidity1 = Float.parseFloat(String.valueOf(a.get(Constants.Sun.HUMIDITY_1)));
                log.info("\t\tHumidity 1: " + humidity1);
                if (sunAnalyses.getHumidity1() != humidity1) {
                    sunAnalyses.setHumidity1(humidity1);
                    save = true;
                }

                float humidity2 = Float.parseFloat(String.valueOf(a.get(Constants.Sun.HUMIDITY_2)));
                log.info("\t\tHumidity 2: " + humidity2);
                if (sunAnalyses.getHumidity2() != humidity2) {
                    sunAnalyses.setHumidity2(humidity2);
                    save = true;
                }

                float soreness = Float.parseFloat(String.valueOf(a.get(Constants.Sun.SORENESS)));
                log.info("\t\tSoreness: " + soreness);
                if (sunAnalyses.getSoreness() != soreness) {
                    sunAnalyses.setSoreness(soreness);
                    save = true;
                }

                float oilImpurity = Float.parseFloat(String.valueOf(a.get(Constants.Sun.OIL_IMPURITY)));
                log.info("\t\tOil impurity: " + oilImpurity);
                if (sunAnalyses.getOilImpurity() != oilImpurity) {
                    sunAnalyses.setOilImpurity(oilImpurity);
                    save = true;
                }

                float acidValue = Float.parseFloat(String.valueOf(a.get(Constants.Sun.ACID_VALUE)));
                log.info("\t\tAcidValue: " + acidValue);
                if (sunAnalyses.getAcidValue() != acidValue) {
                    sunAnalyses.setAcidValue(acidValue);
                    save = true;
                }

                boolean contamination = Boolean.parseBoolean(String.valueOf(a.get("contamination")));
                if (sunAnalyses.isContamination() != contamination) {
                    sunAnalyses.setContamination(contamination);
                    save = true;
                }

                if (save) {
                    ActionTime createTime = sunAnalyses.getCreateTime();
                    if (createTime == null) {
                        createTime = new ActionTime();
                        sunAnalyses.setCreateTime(createTime);
                    }
                    createTime.setTime(new Timestamp(System.currentTimeMillis()));

                    log.info("\t\tCreator: " + creator.getValue());
                    createTime.setCreator(creator);
                    sunAnalyses.setCreator(worker);

                    hibernator.save(createTime, sunAnalyses, analyses);
                    TransportUtil.calculateWeight(loadPlan.getTransportation());

                    Notificator notificator = BotFactory.getNotificator();
                    if (notificator != null) {
                        notificator.sunAnalysesShow(loadPlan, analysesList);
                    }
                }
            }

            for (SunTransportationAnalyses analyses : map.values()) {
                hibernator.remove(analyses);
            }

            write(resp, answer);
        } else {
            write(resp, emptyBody);
        }
    }
}
