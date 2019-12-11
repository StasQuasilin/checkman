package api.warehousing;

import api.ServletAPI;
import api.sockets.Subscriber;
import bot.BotFactory;
import bot.Notificator;
import constants.Branches;
import entity.Subdivision;
import entity.production.Turn;
import entity.transport.ActionTime;
import entity.warehousing.StopReport;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import utils.TurnDateTime;
import utils.turns.TurnBox;
import utils.turns.TurnService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Created by szpt_user045 on 10.12.2019.
 */
@WebServlet(Branches.API.EDIT_STOP_REPORT)
public class EditStopReportAPI extends ServletAPI {

    private final Logger log = Logger.getLogger(EditStopReportAPI.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            log.info(body);
            StopReport report = dao.getObjectById(StopReport.class, body.get(ID));
            if (report == null){
                report = new StopReport();
                report.setCreateTime(new ActionTime(getWorker(req)));
            }
            LocalDateTime now = LocalDateTime.now();
            TurnDateTime turnDate = TurnBox.getTurnDate(now);
            Turn turn = TurnService.getTurn(turnDate);
            report.setTurn(turn);

            Subdivision subdivision = dao.getObjectById(Subdivision.class, body.get(SUBDIVISION));
            report.setSubdivision(subdivision);

            String reason = String.valueOf(body.get(REASON));
            report.setReason(reason);

            JSONObject delay = (JSONObject) body.get(DELAY);
            int days = Integer.parseInt(String.valueOf(delay.get(DAYS)));
            report.setDays(days);
            int hours = Integer.parseInt(String.valueOf(delay.get(HOURS)));
            report.setHours(hours);
            int minutes = Integer.parseInt(String.valueOf(delay.get(MINUTES)));
            report.setMinutes(minutes);

            dao.save(report.getCreateTime());
            dao.save(report);
            write(resp, SUCCESS_ANSWER);
            Notificator notificator = BotFactory.getNotificator();
            if(notificator != null){
                notificator.alarm(report);
            }
        }
    }
}
