package api.laboratory.extraction;

import api.ServletAPI;
import bot.BotFactory;
import bot.TelegramNotificator;
import constants.Branches;
import constants.Constants;
import entity.Worker;
import entity.laboratory.subdivisions.extraction.ExtractionTurn;
import entity.laboratory.subdivisions.extraction.TurnProtein;
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
@WebServlet(Branches.API.EXTRACTION_TURN_PROTEIN_EDIT)
public class ExtractionTurnProteinEditServletAPI extends ServletAPI {

    final UpdateUtil updateUtil = new UpdateUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            TurnProtein turnProtein;
            long id = -1;
            if (body.containsKey(Constants.ID)) {
                id = (long) body.get(Constants.ID);
            }
            if (id != -1){
                turnProtein = dao.getTurnProteinById(id);
            } else {
                turnProtein = new TurnProtein();
            }

            LocalDate date = LocalDate.parse(String.valueOf(body.get("date")));
            long turnId = (long) body.get("turn");
            TurnSettings turn = TurnBox.getTurn(turnId);
            if (turn.getBegin().after(turn.getEnd())) {
                date = date.minusDays(1);
            }

            boolean save = false;
            LocalDateTime turnTime = LocalDateTime.of(date, turn.getBegin().toLocalTime());
            ExtractionTurn targetTurn = TurnService.getExtractionTurn(TurnBox.getTurnDate(turnTime));
            ExtractionTurn currentTurn = turnProtein.getTurn();
            if (currentTurn == null || currentTurn.getId() != targetTurn.getId()){
                turnProtein.setTurn(targetTurn);
                save = true;
            }

            float humidity = Float.parseFloat(String.valueOf(body.get("humidity")));
            if (turnProtein.getHumidity() != humidity) {
                turnProtein.setHumidity(humidity);
                save = true;
            }

            float protein = Float.parseFloat(String.valueOf(body.get("protein")));
            if (turnProtein.getProtein() != protein) {
                turnProtein.setProtein(protein);
                save = true;
            }

            float nuclear = Float.parseFloat(String.valueOf(body.get("nuclear")));
            if (turnProtein.getNuclearGrease() != nuclear){
                turnProtein.setNuclearGrease(nuclear);
                save = true;
            }

            if (save) {
                ActionTime createTime = turnProtein.getCreateTime();
                if (createTime == null) {
                    createTime = new ActionTime();
                    turnProtein.setCreateTime(createTime);
                }
                createTime.setTime(new Timestamp(System.currentTimeMillis()));
                Worker worker = getWorker(req);
                if (body.containsKey(Constants.CREATOR)) {
                    long creatorId = (long) body.get(Constants.CREATOR);
                    createTime.setCreator(dao.getObjectById(creatorId));
                } else {
                    createTime.setCreator(worker);
                }
                turnProtein.setCreator(worker);
                dao.save(createTime, turnProtein);
                updateUtil.onSave(dao.getExtractionTurnByTurn(targetTurn.getTurn()));
                if (currentTurn != null && currentTurn.getId() != targetTurn.getId()){
                    updateUtil.onSave(dao.getExtractionTurnByTurn(currentTurn.getTurn()));
                }
                TelegramNotificator notificator = BotFactory.getTelegramNotificator();
                if (notificator != null) {
                    notificator.extractionShow(turnProtein);
                }
            }

            write(resp, SUCCESS_ANSWER);
        } else {
            write(resp, EMPTY_BODY);
        }
    }
}
