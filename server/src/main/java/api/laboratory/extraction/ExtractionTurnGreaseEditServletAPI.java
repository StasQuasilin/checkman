package api.laboratory.extraction;

import api.ServletAPI;
import bot.BotFactory;
import bot.Notificator;
import constants.Branches;
import constants.Constants;
import entity.Worker;
import entity.laboratory.subdivisions.extraction.ExtractionTurn;
import entity.laboratory.subdivisions.extraction.TurnGrease;
import entity.production.TurnSettings;
import entity.transport.ActionTime;
import org.json.simple.JSONObject;
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

/**
 * Created by szpt_user045 on 16.05.2019.
 */
@WebServlet(Branches.API.EXTRACTION_TURN_GREASE_EDIT)
public class ExtractionTurnGreaseEditServletAPI extends ServletAPI {

    final UpdateUtil updateUtil = new UpdateUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            TurnGrease turnGrease;
            long id = -1;
            if (body.containsKey(Constants.ID)) {
                id = (long) body.get(Constants.ID);
            }
            if (id != -1){
                turnGrease = dao.getTurnGreaseById(id);
            } else {
                turnGrease = new TurnGrease();
            }

            LocalDate date = LocalDate.parse(String.valueOf(body.get("date")));
            long turnId = (long) body.get("turn");
            TurnSettings turn = TurnBox.getTurn(turnId);
            if (turn.getBegin().after(turn.getEnd())) {
                date = date.minusDays(1);
            }
            LocalDateTime turnTime = LocalDateTime.of(date, turn.getBegin().toLocalTime());
            boolean save = false;

            ExtractionTurn targetTurn = TurnService.getExtractionTurn(TurnBox.getTurnDate(turnTime));
            ExtractionTurn currentTurn = turnGrease.getTurn();
            if (currentTurn == null || currentTurn.getId() != targetTurn.getId()){
                turnGrease.setTurn(targetTurn);
                save = true;
            }

            float humidity = Float.parseFloat(String.valueOf(body.get("humidity")));
            if (turnGrease.getHumidity() != humidity) {
                turnGrease.setHumidity(humidity);
                save = true;
            }

            float protein = Float.parseFloat(String.valueOf(body.get("grease")));
            if (turnGrease.getGrease() != protein) {
                turnGrease.setGrease(protein);
                save = true;
            }

            if (save) {
                ActionTime createTime = turnGrease.getCreateTime();
                if (createTime == null) {
                    createTime = new ActionTime();
                    turnGrease.setCreateTime(createTime);
                }
                createTime.setTime(new Timestamp(System.currentTimeMillis()));
                Worker worker = getWorker(req);
                if (body.containsKey(Constants.CREATOR)) {
                    long creatorId = (long) body.get(Constants.CREATOR);
                    createTime.setCreator(dao.getObjectById(creatorId));
                } else {
                    createTime.setCreator(worker);
                }
                turnGrease.setCreator(worker);
                dao.save(createTime, turnGrease);
                updateUtil.onSave(dao.getExtractionTurnByTurn(targetTurn.getTurn()));
                if (currentTurn != null && currentTurn.getId() != targetTurn.getId()){
                    updateUtil.onSave(dao.getExtractionTurnByTurn(currentTurn.getTurn()));
                }
                Notificator notificator = BotFactory.getNotificator();
                if (notificator != null){
                    notificator.extractionShow(turnGrease);
                }

            }

            write(resp, SUCCESS_ANSWER);
        } else {
            write(resp, EMPTY_BODY);
        }
    }
}
