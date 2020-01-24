package api.reports;

import api.ServletAPI;
import bot.TelegramBotFactory;
import bot.TelegramNotificator;
import constants.Branches;
import entity.reports.ManufactureReport;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.telegram.telegrambots.meta.api.objects.Message;
import utils.ManufactureReportUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by szpt_user045 on 11.11.2019.
 */
@WebServlet(Branches.API.MANUFACTURE_REPORT_SEND)
public class SendReportAPI extends ServletAPI {

    final ManufactureReportUtil util = new ManufactureReportUtil();
    final Logger log = Logger.getLogger(SendReportAPI.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            ManufactureReport report = dao.getObjectById(ManufactureReport.class, body.get(ID));
            TelegramNotificator notificator = TelegramBotFactory.getTelegramNotificator();
            if (notificator != null) {
                util.removeChatMessages(report, notificator);

                ArrayList<Message> messages = new ArrayList<>();
                notificator.manufactureReportShow(report, messages);

                util.saveMessages(report, messages);
                write(resp, SUCCESS_ANSWER);
            } else {
                log.warn("Can't send report to Telegram");
            }
        }
    }
}
