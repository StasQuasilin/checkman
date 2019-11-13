package api.laboratory;

import api.ServletAPI;
import bot.BotFactory;
import bot.Notificator;
import constants.Branches;
import constants.Constants;
import entity.Worker;
import entity.laboratory.SunAnalyses;
import entity.transport.ActionTime;
import entity.transport.Transportation;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import utils.TransportUtil;
import utils.U;
import utils.UpdateUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;

/**
 * Created by szpt_user045 on 27.03.2019.
 */
@WebServlet(Branches.API.LABORATORY_SAVE_SUN)
public class EditSunServletAPI extends ServletAPI {

    private final Logger log = Logger.getLogger(EditSunServletAPI.class);
    final UpdateUtil updateUtil = new UpdateUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            long planId = (long) body.get(Constants.PLAN);
            log.info("Edit SUN analyses for plan \'" + planId + "\'...");
            final Transportation transportation = dao.getTransportationById(planId);

            boolean save = false;
            SunAnalyses sunAnalyses = transportation.getSunAnalyses();
            if (sunAnalyses == null) {
                sunAnalyses = new SunAnalyses();
                transportation.setSunAnalyses(sunAnalyses);
            }

            JSONObject a = (JSONObject) body.get("analyses");
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

            String hum2String = String.valueOf(a.get(Constants.Sun.HUMIDITY_2));
            if (U.exist(hum2String)) {
                float humidity2 = Float.parseFloat(hum2String);
                log.info("\t\tHumidity 2: " + humidity2);
                if (sunAnalyses.getHumidity2() != humidity2) {
                    sunAnalyses.setHumidity2(humidity2);
                    save = true;
                }
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
                Worker worker = getWorker(req);
                Worker creator;
                if (a.containsKey(Constants.CREATOR)) {
                    long creatorId = (long) a.get(Constants.CREATOR);
                    log.info("\t\tHave creator");
                    creator = dao.getWorkerById(creatorId);
                } else {
                    log.info("\t\tDoesn't have creator");
                    creator = worker;
                }

                log.info("\t\tCreator: " + creator.getValue());
                createTime.setCreator(creator);

                dao.save(createTime, sunAnalyses, transportation);
                float v = 0;
//                float v = TransportUtil.calculateWeight(transportation);
//                updateUtil.onSave(transportation);

                Notificator notificator = BotFactory.getNotificator();
                if (notificator != null) {
                    notificator.sunAnalysesShow(transportation, sunAnalyses, 1f * Math.round(v * 100) / 100);
                }
            }
            write(resp, SUCCESS_ANSWER);
        } else {
            write(resp, EMPTY_BODY);
        }
    }
}
