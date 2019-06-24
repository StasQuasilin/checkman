package api.laboratory.extraction;

import api.API;
import bot.BotFactory;
import constants.Branches;
import constants.Constants;
import entity.Worker;
import entity.laboratory.subdivisions.extraction.ExtractionTurn;
import entity.laboratory.subdivisions.extraction.TurnProtein;
import entity.production.TurnSettings;
import entity.transport.ActionTime;
import org.json.simple.JSONObject;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;
import utils.turns.ExtractionTurnService;
import utils.turns.TurnBox;

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
public class ExtractionTurnProteinEdit extends API {



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
                turnProtein = dao.getExtractionTurnProteinById(id);
            } else {
                turnProtein = new TurnProtein();
            }

            LocalDate date = LocalDate.parse(String.valueOf(body.get("date")));
            long turnId = (long) body.get("turn");
            TurnSettings turn = TurnBox.getBox().getTurn(turnId);
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

            turnProtein.setTurn(extractionTurn);
            boolean save = false;

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
                    createTime.setCreator(dao.getWorkerById(creatorId));
                } else {
                    createTime.setCreator(worker);
                }
                turnProtein.setCreator(worker);
                dao.save(createTime, turnProtein);
                BotFactory.getNotificator().extractionShow(turnProtein);
            }

            write(resp, answer);
        } else {
            write(resp, emptyBody);
        }
    }
}
