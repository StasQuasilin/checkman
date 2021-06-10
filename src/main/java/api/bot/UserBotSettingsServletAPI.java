package api.bot;

import api.ServletAPI;
import bot.BotSettings;
import constants.Branches;
import constants.Constants;
import entity.bot.NotifyStatus;
import entity.bot.UserNotificationSetting;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 17.04.2019.
 */
@WebServlet(Branches.API.USER_BOT_SETTINGS)
public class UserBotSettingsServletAPI extends ServletAPI {

    final BotSettings botSettings = BotSettings.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            UserNotificationSetting setting;
            if (body.containsKey(Constants.ID)) {
                setting = botSettings.getSettings((long) body.get(Constants.TELEGRAM_ID));
            } else {
                setting = dao.getUseBorSettingsByWorker(getWorker(req));
            }

            if (setting != null) {
                boolean save = false;

                NotifyStatus transport = NotifyStatus.valueOf(String.valueOf(body.get("transport")));
                if (setting.getTransport() != transport) {
                    setting.setTransport(transport);
                    save = true;
                }
                NotifyStatus weight = NotifyStatus.valueOf(String.valueOf(body.get("weight")));
                if (setting.getWeight() != weight) {
                    setting.setWeight(weight);
                    save = true;
                }
                NotifyStatus analyses = NotifyStatus.valueOf(String.valueOf(body.get("analyses")));
                if (setting.getAnalyses() != analyses) {
                    setting.setAnalyses(analyses);
                    save = true;
                }
                boolean extraction = Boolean.parseBoolean(String.valueOf(body.get("extraction")));
                if (setting.isExtraction() != extraction) {
                    setting.setExtraction(extraction);
                    save = true;
                }
                boolean vro = Boolean.parseBoolean(String.valueOf(body.get("vro")));
                if (setting.isVro() != vro) {
                    setting.setVro(vro);
                    save = true;
                }
                boolean kpo = Boolean.parseBoolean(String.valueOf(body.get("kpo")));
                if (setting.isKpo() != kpo) {
                    setting.setKpo(kpo);
                    save = true;
                }
                boolean show = Boolean.parseBoolean(String.valueOf(body.get("show")));
                if (setting.isShow() != show) {
                    setting.setShow(show);
                    save = true;
                }
                boolean manufactureReports = Boolean.parseBoolean(String.valueOf(body.get("manufactureReports")));
                if (setting.isManufactureReports() != manufactureReports){
                    setting.setManufactureReports(manufactureReports);
                    save = true;
                }
                boolean roundReports = Boolean.parseBoolean(String.valueOf(body.get("roundReports")));
                if (setting.isRoundReport() != roundReports){
                    setting.setRoundReport(roundReports);
                    save = true;
                }

                if (save) {
                    botSettings.save(setting);
                }
                write(resp, parser.toJson(setting).toJSONString());

            }

        }
    }
}
