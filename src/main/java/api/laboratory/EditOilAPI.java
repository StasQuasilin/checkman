package api.laboratory;

import api.API;
import bot.BotFactory;
import bot.Notificator;
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
@WebServlet(Branches.API.LABORATORY_SAVE_OIL)
public class EditOilAPI extends API {


    private final Logger log = Logger.getLogger(EditOilAPI.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            long planId = (long) body.get(Constants.PLAN);
            log.info("Edit OIL analyses for plan \'" + planId + "\'...");

            LoadPlan loadPlan = hibernator.get(LoadPlan.class, "id", planId);

            boolean save = false;
            OilAnalyses oilAnalyses = loadPlan.getTransportation().getOilAnalyses();
            if (oilAnalyses == null){
                oilAnalyses = new OilAnalyses();
                loadPlan.getTransportation().setOilAnalyses(oilAnalyses);
            }

            boolean organoleptic = (boolean) body.get(Constants.Oil.ORGANOLEPTIC);
            log.info("\t\tOrganoleptic: " + organoleptic);
            if (oilAnalyses.isOrganoleptic() != organoleptic) {
                oilAnalyses.setOrganoleptic(organoleptic);
                save = true;
            }

            int color = Integer.parseInt(String.valueOf(body.get(Constants.Oil.COLOR)));
            log.info("\t\tColor: " + color);
            if (oilAnalyses.getColor() != color) {
                oilAnalyses.setColor(color);
                save = true;
            }

            float acidValue = Float.parseFloat(String.valueOf(body.get(Constants.Oil.ACID_VALUE)));
            log.info("\t\tAcid value: " + acidValue);
            if (oilAnalyses.getAcidValue() != acidValue) {
                oilAnalyses.setAcidValue(acidValue);
                save = true;
            }

            float peroxideValue = Float.parseFloat(String.valueOf(body.get(Constants.Oil.PEROXIDE_VALUE)));
            log.info("\t\tPeroxide value: " + peroxideValue);
            if (oilAnalyses.getPeroxideValue() != peroxideValue) {
                oilAnalyses.setPeroxideValue(peroxideValue);
                save = true;
            }

            float phosphorus = Float.parseFloat(String.valueOf(body.get(Constants.Oil.PHOSPHORUS)));
            log.info("\t\tPhosphorus: " + phosphorus);
            if (oilAnalyses.getPhosphorus() != phosphorus) {
                oilAnalyses.setPhosphorus(phosphorus);
                save = true;
            }

            float humidity = Float.parseFloat(String.valueOf(body.get(Constants.Oil.HUMIDITY )));
            log.info("\t\tHumidity: " + humidity);
            if (oilAnalyses.getHumidity() != humidity) {
                oilAnalyses.setHumidity(humidity);
                save = true;
            }

            boolean soap = Boolean.parseBoolean(String.valueOf(body.get(Constants.Oil.SOAP)));
            log.info("\t\tSoap: " + soap);
            if (oilAnalyses.isSoap() != soap) {
                oilAnalyses.setSoap(soap);
                save = true;
            }

            float wax = Float.parseFloat(String.valueOf(body.get(Constants.Oil.WAX)));
            log.info("\t\tWax: " + wax);
            if (oilAnalyses.getWax() != wax) {
                oilAnalyses.setWax(wax);
                save = true;
            }

            if (save) {
                ActionTime createTime = oilAnalyses.getCreateTime();
                if (createTime == null) {
                    createTime = new ActionTime();
                    oilAnalyses.setCreateTime(createTime);
                }
                createTime.setTime(new Timestamp(System.currentTimeMillis()));
                Worker worker = getWorker(req);
                if (body.containsKey(Constants.CREATOR)) {
                    log.info("\t\tHave creator");
                    createTime.setCreator(hibernator.get(Worker.class, "id", body.get(Constants.CREATOR)));
                } else {
                    log.info("\t\tDoesn't have creator");
                    createTime.setCreator(worker);
                }
                log.info("\t\tCreator: " + createTime.getCreator().getValue());
                oilAnalyses.setCreator(worker);

                hibernator.save(oilAnalyses.getCreateTime(), oilAnalyses);

                Notificator notificator = BotFactory.getNotificator();
                if (notificator != null) {
                    notificator.oilAnalysesShow(loadPlan, oilAnalyses);
                }
            }
            write(resp, answer);
        } else {
            write(resp, emptyBody);
        }
    }
}
