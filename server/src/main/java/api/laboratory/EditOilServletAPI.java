package api.laboratory;

import api.ServletAPI;
import bot.BotFactory;
import bot.Notificator;
import constants.Branches;
import constants.Constants;
import entity.Worker;
import entity.laboratory.OilAnalyses;
import entity.transport.ActionTime;
import entity.transport.Transportation;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
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
@WebServlet(Branches.API.LABORATORY_SAVE_OIL)
public class EditOilServletAPI extends ServletAPI {

    private final Logger log = Logger.getLogger(EditOilServletAPI.class);
    final UpdateUtil updateUtil = new UpdateUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            long planId = (long) body.get(Constants.PLAN);
            log.info("Edit OIL analyses for plan \'" + planId + "\'...");

            Transportation transportation = dao.getTransportationById(planId);

            boolean save = false;
            OilAnalyses oilAnalyses = transportation.getOilAnalyses();
            if (oilAnalyses == null){
                oilAnalyses = new OilAnalyses();
                transportation.setOilAnalyses(oilAnalyses);
            }
            
            JSONObject a = (JSONObject) body.get("analyses");
            boolean organoleptic = (boolean) a.get(Constants.Oil.ORGANOLEPTIC);
            log.info("\t\tOrganoleptic: " + organoleptic);
            if (oilAnalyses.isOrganoleptic() != organoleptic) {
                oilAnalyses.setOrganoleptic(organoleptic);
                save = true;
            }

            float color = Float.parseFloat(String.valueOf(a.get(Constants.Oil.COLOR)));
            log.info("\t\tColor: " + color);
            if (oilAnalyses.getColor() != color) {
                oilAnalyses.setColor(color);
                save = true;
            }

            float acidValue = Float.parseFloat(String.valueOf(a.get(Constants.Oil.ACID_VALUE)));
            log.info("\t\tAcid value: " + acidValue);
            if (oilAnalyses.getAcidValue() != acidValue) {
                oilAnalyses.setAcidValue(acidValue);
                save = true;
            }

            float peroxideValue = Float.parseFloat(String.valueOf(a.get(Constants.Oil.PEROXIDE_VALUE)));
            log.info("\t\tPeroxide value: " + peroxideValue);
            if (oilAnalyses.getPeroxideValue() != peroxideValue) {
                oilAnalyses.setPeroxideValue(peroxideValue);
                save = true;
            }

            float phosphorus = Float.parseFloat(String.valueOf(a.get(Constants.Oil.PHOSPHORUS)));
            log.info("\t\tPhosphorus: " + phosphorus);
            if (oilAnalyses.getPhosphorus() != phosphorus) {
                oilAnalyses.setPhosphorus(phosphorus);
                save = true;
            }

            float humidity = Float.parseFloat(String.valueOf(a.get(Constants.Oil.HUMIDITY )));
            log.info("\t\tHumidity: " + humidity);
            if (oilAnalyses.getHumidity() != humidity) {
                oilAnalyses.setHumidity(humidity);
                save = true;
            }

            boolean soap = Boolean.parseBoolean(String.valueOf(a.get(Constants.Oil.SOAP)));
            log.info("\t\tSoap: " + soap);
            if (oilAnalyses.isSoap() != soap) {
                oilAnalyses.setSoap(soap);
                save = true;
            }

            float degreaseImpurity = Float.parseFloat(String.valueOf(a.get("degreaseImpurity")));
            log.info("\t\tDegreaseImpurity: " + degreaseImpurity);
            if (oilAnalyses.getDegreaseImpurity() != degreaseImpurity) {
                oilAnalyses.setDegreaseImpurity(degreaseImpurity);
                save = true;
            }

            float transparency = Float.parseFloat(String.valueOf(a.get("transparency")));
            log.info("\t\tTransparency: " + transparency);
            if (oilAnalyses.getTransparency() != transparency) {
                oilAnalyses.setTransparency(transparency);
                save = true;
            }

            float benzopyrene = Float.parseFloat(String.valueOf(a.get("benzopyrene")));
            log.info("\t\tBenzopyrene: " + benzopyrene);
            if (oilAnalyses.getBenzopyrene() != benzopyrene) {
                oilAnalyses.setBenzopyrene(benzopyrene);
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
                if (a.containsKey(Constants.CREATOR)) {
                    log.info("\t\tHave creator");
                    createTime.setCreator(dao.getWorkerById(a.get(Constants.CREATOR)));
                } else {
                    log.info("\t\tDoesn't have creator");
                    createTime.setCreator(worker);
                }

                dao.save(oilAnalyses.getCreateTime(), oilAnalyses, transportation);
                updateUtil.onSave(transportation);

                Notificator notificator = BotFactory.getNotificator();
                if (notificator != null) {
                    notificator.oilAnalysesShow(transportation, oilAnalyses);
                }
            }
            write(resp, SUCCESS_ANSWER);
        } else {
            write(resp, emptyBody);
        }
    }
}
