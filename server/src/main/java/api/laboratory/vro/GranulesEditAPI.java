package api.laboratory.vro;

import api.ServletAPI;
import bot.BotFactory;
import bot.Notificator;
import constants.Branches;
import constants.Constants;
import entity.Worker;
import entity.laboratory.subdivisions.vro.GranulesAnalyses;
import entity.laboratory.subdivisions.vro.VROTurn;
import entity.production.TurnSettings;
import entity.transport.ActionTime;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import utils.TurnDateTime;
import utils.UpdateUtil;
import utils.turns.TurnBox;
import utils.turns.TurnService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Created by szpt_user045 on 29.10.2019.
 */
@WebServlet(Branches.API.VRO_GRANULES_EDIT)
public class GranulesEditAPI extends ServletAPI {

    private final Logger log = Logger.getLogger(VRODailyEditServletAPI.class);
    final UpdateUtil updateUtil = new UpdateUtil();
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            log.info(body);
            LocalDate date = LocalDate.parse(String.valueOf(body.get(DATE)));
//            int turnNumber = Integer.parseInt(String.valueOf(body.get(TURN)));

            TurnSettings turn = TurnBox.getTurnByNumber(TurnBox.getTurns().get(0).getNumber());
            LocalTime time = turn.getBegin().toLocalTime();
            LocalDateTime localDateTime = LocalDateTime.of(date, time);
            TurnDateTime turnDate = TurnBox.getTurnDate(localDateTime);

            boolean save = false;
            GranulesAnalyses analyses;
            if (body.containsKey(ID)){
                analyses = dao.getObjectById(GranulesAnalyses.class, body.get(ID));
            } else {
                analyses = new GranulesAnalyses();
            }

            VROTurn targetTurn = TurnService.getVROTurn(turnDate);
            VROTurn currentTurn = analyses.getTurn();
            if (currentTurn == null || currentTurn.getId() != targetTurn.getId()){
                analyses.setTurn(targetTurn);
                save = true;
            }

            float density = Float.parseFloat(String.valueOf(body.get("density")));
            if (analyses.getDensity() != density) {
                analyses.setDensity(density);
                save = true;
            }

            float humidity = Float.parseFloat(String.valueOf(body.get("humidity")));
            if (analyses.getHumidity() != humidity) {
                analyses.setHumidity(humidity);
                save = true;
            }

            float dust = Float.parseFloat(String.valueOf(body.get("dust")));
            if (analyses.getDust() != dust) {
                analyses.setDust(dust);
                save = true;
            }

            boolean match = Boolean.parseBoolean(String.valueOf(body.get("match")));
            if (analyses.isMatch() != match) {
                analyses.setMatch(match);
                save = true;
            }

            if (save) {
                ActionTime createTime = analyses.getCreateTime();
                if (createTime == null) {
                    createTime = new ActionTime();
                    analyses.setCreateTime(createTime);
                }
                createTime.setTime(new Timestamp(System.currentTimeMillis()));
                Worker worker = getWorker(req);
                if (body.containsKey(Constants.CREATOR)) {
                    long creatorId = (long) body.get(Constants.CREATOR);
                    createTime.setCreator(dao.getObjectById(creatorId));
                } else {
                    createTime.setCreator(worker);
                }
                dao.save(createTime, analyses);
                updateUtil.onSave(dao.getVROTurnByTurn(targetTurn.getTurn()));
                if (currentTurn != null && currentTurn.getId() != targetTurn.getId()) {
                    updateUtil.onSave(dao.getVROTurnByTurn(currentTurn.getTurn()));
                }
                Notificator notificator = BotFactory.getNotificator();
                if (notificator != null) {
                    notificator.show(analyses);
                }
            }
            write(resp, SUCCESS_ANSWER);
        } else {
            write(resp, EMPTY_BODY);
        }
    }
}
