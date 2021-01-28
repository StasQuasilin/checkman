package api.reports;

import api.ServletAPI;
import bot.TelegramBotFactory;
import bot.TelegramNotificator;
import constants.Branches;
import entity.reports.ManufactureReport;
import org.json.simple.JSONObject;
import utils.ManufactureReportUtil;
import utils.UpdateUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 11.11.2019.
 */
@WebServlet(Branches.API.MANUFACTURE_REPORT_REMOVE)
public class RemoveReportAPI extends ServletAPI {

    final ManufactureReportUtil util = new ManufactureReportUtil();
    final UpdateUtil updateUtil = new UpdateUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            ManufactureReport report = dao.getObjectById(ManufactureReport.class, body.get(ID));
            TelegramNotificator notificator = TelegramBotFactory.getTelegramNotificator();
            if (notificator != null) {
                util.removeChatMessages(report, notificator);
            }
            dao.remove(report);
            updateUtil.onRemove(report);
            write(resp, SUCCESS_ANSWER);
        }
    }
}
