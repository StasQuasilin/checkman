package api.bot;

import api.ServletAPI;
import bot.TelegramBotFactory;
import constants.Branches;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 20.04.2019.
 */
@WebServlet(Branches.API.BOT_STATUS)
public class BotStatusServletAPI extends ServletAPI {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject json = pool.getObject();
        json.put("token", TelegramBotFactory.getToken());
        json.put("name", TelegramBotFactory.getName());
        json.put("status", TelegramBotFactory.getStatus().toString());
        write(resp, json.toString());
    }
}
