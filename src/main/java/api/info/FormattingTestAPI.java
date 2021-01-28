package api.info;

import api.ServletAPI;
import bot.TelegramBotFactory;
import bot.TelegramNotificator;
import constants.Branches;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 24.10.2019.
 */
@WebServlet(Branches.API.FORMATTING_TEST)
public class FormattingTestAPI extends ServletAPI {

    public static final long telegramId = -357665329;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            String text = String.valueOf(body.get("text"));
            TelegramNotificator notificator = TelegramBotFactory.getTelegramNotificator();
            if (notificator != null) {
                notificator.send(telegramId, text);
            }
        }
    }
}
