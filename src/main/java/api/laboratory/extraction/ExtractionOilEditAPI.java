package api.laboratory.extraction;

import api.IAPI;
import bot.BotFactory;
import constants.Branches;
import constants.Constants;
import entity.Worker;
import entity.laboratory.subdivisions.extraction.ExtractionOIl;
import entity.laboratory.subdivisions.extraction.ExtractionTurn;
import entity.production.Turn;
import entity.transport.ActionTime;
import org.json.simple.JSONObject;
import utils.ExtractionTurnService;
import utils.PostUtil;
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
 * Created by szpt_user045 on 10.04.2019.
 */
@WebServlet(Branches.API.EXTRACTION_OIL_EDIT)
public class ExtractionOilEditAPI extends IAPI {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = PostUtil.parseBodyJson(req);
        if (body != null) {
            System.out.println(body);
            ExtractionOIl oil;
            long id = -1;
            if (body.containsKey(Constants.ID)) {
                id = (long) body.get(Constants.ID);
            }
            if (id != -1){
                oil = hibernator.get(ExtractionOIl.class, "id", id);
            } else {
                oil = new ExtractionOIl();
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

            oil.setTurn(extractionTurn);
            boolean save = false;

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
                    createTime.setCreator(hibernator.get(Worker.class, "id", creatorId));
                } else {
                    createTime.setCreator(worker);
                }
                oil.setCreator(worker);
                hibernator.save(createTime, oil);
                BotFactory.getNotificator().extractionShow(oil);
            }

            write(resp, answer);
        } else {
            write(resp, emptyBody);
        }
    }
}
