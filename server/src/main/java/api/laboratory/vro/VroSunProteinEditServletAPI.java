package api.laboratory.vro;

import api.ServletAPI;
import bot.BotFactory;
import bot.Notificator;
import constants.Branches;
import constants.Constants;
import entity.Worker;
import entity.laboratory.probes.SunProbe;
import entity.laboratory.subdivisions.extraction.ExtractionTurn;
import entity.laboratory.subdivisions.extraction.TurnProtein;
import entity.laboratory.subdivisions.vro.SunProtein;
import entity.laboratory.subdivisions.vro.VROTurn;
import entity.production.TurnSettings;
import entity.transport.ActionTime;
import org.apache.log4j.Logger;
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
@WebServlet(Branches.API.VRO_SUN_PROTEIN)
public class VroSunProteinEditServletAPI extends ServletAPI {

    private final Logger log = Logger.getLogger(VroSunProteinEditServletAPI.class);
    final UpdateUtil updateUtil = new UpdateUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            log.info(body);
            SunProtein sunProtein;
            long id = -1;
            if (body.containsKey(Constants.ID)) {
                id = (long) body.get(Constants.ID);
            }
            if (id != -1){
                sunProtein = dao.getObjectById(SunProtein.class, id);
            } else {
                sunProtein = new SunProtein();
            }

            LocalDate date = LocalDate.parse(String.valueOf(body.get("date")));
            long turnId = (long) body.get("turn");
            TurnSettings turn = TurnBox.getTurn(turnId);
            if (turn.getBegin().after(turn.getEnd())) {
                date = date.minusDays(1);
            }

            boolean save = false;
            LocalDateTime turnTime = LocalDateTime.of(date, turn.getBegin().toLocalTime());
            VROTurn targetTurn = TurnService.getVROTurn(TurnBox.getTurnDate(turnTime));
            VROTurn currentTurn = sunProtein.getTurn();

            if (currentTurn == null || currentTurn.getId() != targetTurn.getId()){
                sunProtein.setTurn(targetTurn);
                save = true;
            }

            float protein = Float.parseFloat(String.valueOf(body.get("protein")));
            if (sunProtein.getProtein() != protein) {
                log.info("Protein: " + protein);
                sunProtein.setProtein(protein);
                save = true;
            }

            float humidity = Float.parseFloat(String.valueOf(body.get("humidity")));
            if (sunProtein.getHumidity() != humidity) {
                log.info("Humidity: " + humidity);
                sunProtein.setHumidity(humidity);
                save = true;
            }

            if (save) {
                ActionTime createTime = sunProtein.getCreateTime();
                if (createTime == null) {
                    createTime = new ActionTime();
                    sunProtein.setCreateTime(createTime);
                }
                createTime.setTime(new Timestamp(System.currentTimeMillis()));
                Worker worker = getWorker(req);
                if (body.containsKey(Constants.CREATOR)) {
                    long creatorId = (long) body.get(Constants.CREATOR);
                    createTime.setCreator(dao.getWorkerById(creatorId));
                } else {
                    createTime.setCreator(worker);
                }
                dao.save(createTime, sunProtein);
                updateUtil.onSave(dao.getVROTurnByTurn(targetTurn.getTurn()));
                if (currentTurn != null && currentTurn.getId() != targetTurn.getId()){
                    updateUtil.onSave(dao.getVROTurnByTurn(currentTurn.getTurn()));
                }
                Notificator notificator = BotFactory.getNotificator();
                if (notificator != null) {
                    notificator.show(sunProtein);
                }
            }

            write(resp, SUCCESS_ANSWER);
        } else {
            write(resp, EMPTY_BODY);
        }
    }
}
