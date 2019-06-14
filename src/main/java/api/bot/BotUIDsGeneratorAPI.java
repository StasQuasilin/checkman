package api.bot;

import api.API;
import bot.BotUIDs;
import constants.Branches;
import utils.JsonParser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 16.04.2019.
 */
@WebServlet(Branches.API.BOT_UID)
public class BotUIDsGeneratorAPI extends API {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        write(resp, JsonParser.toJson(BotUIDs.getBox().generateToken(getWorker(req))).toJSONString());
    }
}
