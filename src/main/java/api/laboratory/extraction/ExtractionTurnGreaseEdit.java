package api.laboratory.extraction;

import api.IAPI;
import bot.BotFactory;
import constants.Branches;
import constants.Constants;
import entity.Worker;
import entity.laboratory.subdivisions.extraction.ExtractionTurn;
import entity.laboratory.subdivisions.extraction.TurnGrease;
import entity.production.Turn;
import entity.transport.ActionTime;
import org.json.simple.JSONObject;
import utils.ExtractionTurnService;
import utils.TurnBox;

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
public class ExtractionTurnGreaseEdit extends IAPI {
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
                turnGrease = hibernator.get(TurnGrease.class, "id", id);
            } else {
                turnGrease = new TurnGrease();
            }

            LocalDate date = LocalDate.parse(String.valueOf(body.get("date")));
            long turnId = (long) body.get("turn");
            Turn turn = TurnBox.getBox().getTurn(turnId);
            if (turn.getBegin().after(turn.getEnd())) {
                date = date.minusDays(1);
            }
            LocalDateTime turnTime = LocalDateTime.of(
                    date.getYear(),
                    date.getMonth(),
                    date.getDayOfMonth(),
                    turn.getBegin().toLocalTime().getHour(),
                    turn.getBegin().toLocalTime().getMinute());
            ExtractionTurn extractionTurn = ExtractionTurnService.getTurn(TurnBox.getBox().getTurnDate(turnTime));

            turnGrease.setTurn(extractionTurn);
            boolean save = false;

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
                    createTime.setCreator(hibernator.get(Worker.class, "id", creatorId));
                } else {
                    createTime.setCreator(worker);
                }
                turnGrease.setCreator(worker);
                hibernator.save(createTime, turnGrease);
                BotFactory.getNotificator().extractionShow(turnGrease);
            }

            write(resp, answer);
        } else {
            write(resp, emptyBody);
        }
    }
}
