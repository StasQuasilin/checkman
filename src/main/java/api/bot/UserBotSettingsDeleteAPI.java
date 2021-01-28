package api.bot;

import api.ServletAPI;
import constants.Branches;
import constants.Constants;
import entity.bot.BotSettings;
import entity.bot.UserBotSetting;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 02.10.2019.
 */
@WebServlet(Branches.API.USER_BOT_DELETE)
public class UserBotSettingsDeleteAPI extends ServletAPI {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            UserBotSetting settings = dao.getObjectById(UserBotSetting.class, body.get(Constants.ID));
            if (settings != null) {
                dao.remove(settings);
                write(resp, SUCCESS_ANSWER);
            }
        }
    }
}
