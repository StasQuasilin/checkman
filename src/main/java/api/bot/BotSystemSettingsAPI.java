package api.bot;

import api.IAPI;
import bot.BotFactory;
import constants.Branches;
import entity.bot.BotSettings;
import org.json.simple.JSONObject;
import utils.U;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 20.04.2019.
 */
@WebServlet(Branches.API.BOT_SETTINGS)
public class BotSystemSettingsAPI extends IAPI{

    BotSettings settings;

    @Override
    public void init() throws ServletException {
        settings = hibernator.get(BotSettings.class, null);
        if (settings != null) {
            try {
                BotFactory.setSettings(settings);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        String token = (String) body.get("token");
        String name = (String) body.get("name");
        boolean save = false;
        if (U.exist(token)){
            settings.setToken(token);
            save = true;
        }

        if (U.exist(name)){
            settings.setName(name);
            save = true;
        }

        if (save){
            hibernator.save(settings);
            BotFactory.setSettings(settings);
        }

        write(resp, answer);
    }


}
