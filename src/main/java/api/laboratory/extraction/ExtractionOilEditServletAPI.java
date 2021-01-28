package api.laboratory.extraction;

import api.ServletAPI;
import bot.TelegramBotFactory;
import bot.TelegramNotificator;
import constants.Branches;
import constants.Constants;
import entity.Worker;
import entity.laboratory.subdivisions.extraction.ExtractionOIl;
import entity.laboratory.subdivisions.extraction.ExtractionTurn;
import entity.production.TurnSettings;
import entity.transport.ActionTime;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import utils.UpdateUtil;
import utils.PostUtil;
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
 * Created by szpt_user045 on 10.04.2019.
 */
@WebServlet(Branches.API.EXTRACTION_OIL_EDIT)
public class ExtractionOilEditServletAPI extends ServletAPI {

    private final Logger log = Logger.getLogger(ExtractionOilEditServletAPI.class);
    final UpdateUtil updateUtil = new UpdateUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = PostUtil.parseBodyJson(req);
        if (body != null) {
            log.info(body);
            ExtractionOIl oil;
            long id = -1;
            if (body.containsKey(Constants.ID)) {
                id = (long) body.get(Constants.ID);
            }
            if (id != -1){
                oil = dao.getExtractionOilById(id);
            } else {
                oil = new ExtractionOIl();
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
            ExtractionTurn currentTurn = oil.getTurn();
            if (currentTurn == null || currentTurn.getId() != targetTurn.getId()) {
                oil.setTurn(targetTurn);
                save = true;
            }

            float humidity = Float.parseFloat(String.valueOf(body.get("humidity")));
            if (oil.getHumidity() != humidity) {
                oil.setHumidity(humidity);
                save = true;
            }

            float acid = Float.parseFloat(String.valueOf(body.get("acid")));
            if (oil.getAcid() != acid) {
                oil.setAcid(acid);
                save = true;
            }

            float peroxide = Float.parseFloat(String.valueOf(body.get("peroxide")));
            if (oil.getPeroxide() != peroxide) {
                oil.setPeroxide(peroxide);
                save = true;
            }

            float phosphorus = Float.parseFloat(String.valueOf(body.get("phosphorus")));
            if (oil.getPhosphorus() != phosphorus) {
                oil.setPhosphorus(phosphorus);
                save = true;
            }

            float explosion = Float.parseFloat(String.valueOf(body.get("explosion")));
            if (oil.getExplosionT() != explosion) {
                oil.setExplosionT(explosion);
                save = true;
            }

            if (save) {
                ActionTime createTime = oil.getCreateTime();
                if (createTime == null) {
                    createTime = new ActionTime();
                    oil.setCreateTime(createTime);
                }
                createTime.setTime(new Timestamp(System.currentTimeMillis()));
                Worker worker = getWorker(req);
                if (body.containsKey(Constants.CREATOR)) {
                    long creatorId = (long) body.get(Constants.CREATOR);
                    createTime.setCreator(dao.getObjectById(creatorId));

                } else {
                    createTime.setCreator(worker);
                }
                oil.setCreator(worker);
                dao.save(createTime, oil);
                updateUtil.onSave(dao.getExtractionTurnByTurn(targetTurn.getTurn()));
                if (currentTurn != null && currentTurn.getId() != targetTurn.getId()){
                    updateUtil.onSave(dao.getExtractionTurnByTurn(currentTurn.getTurn()));
                }
                TelegramNotificator notificator = TelegramBotFactory.getTelegramNotificator();
                if (notificator != null) {
                    notificator.extractionShow(oil);
                }
            }

            write(resp, SUCCESS_ANSWER);
        } else {
            write(resp, EMPTY_BODY);
        }
    }
}
