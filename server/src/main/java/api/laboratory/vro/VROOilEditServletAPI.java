package api.laboratory.vro;

import api.ServletAPI;
import bot.BotFactory;
import bot.Notificator;
import constants.Branches;
import constants.Constants;
import entity.Worker;
import entity.laboratory.subdivisions.vro.VROOil;
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

/**
 * Created by szpt_user045 on 11.04.2019.
 */
@WebServlet(Branches.API.VRO_OIL_EDIT)
public class VROOilEditServletAPI extends ServletAPI {

    private final Logger log = Logger.getLogger(VROOilEditServletAPI.class);

    private final UpdateUtil updateUtil = new UpdateUtil();


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            log.info(body);
            VROOil oil;
            if (body.containsKey(Constants.ID)) {
                long id = (long) body.get(Constants.ID);
                oil = dao.getVROOilById(id);
            } else {
                oil = new VROOil();
            }

            LocalDate date = LocalDate.parse(String.valueOf(body.get("date")));
            long turnId = (long) body.get("turn");
            TurnSettings turn = TurnBox.getTurn(turnId);
            if (turn.getBegin().after(turn.getEnd())) {
                date = date.minusDays(1);
            }
            LocalDateTime turnTime = LocalDateTime.of(date, turn.getBegin().toLocalTime());
            TurnDateTime turnDate = TurnBox.getTurnDate(turnTime);

            boolean save = false;

            VROTurn targetTurn = TurnService.getVROTurn(turnDate);
            VROTurn currentTurn = oil.getTurn();
            if (currentTurn == null || currentTurn.getId() != targetTurn.getId()){
                oil.setTurn(targetTurn);
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

            float color = Float.parseFloat(String.valueOf(body.get("color")));
            if (oil.getColor() != color) {
                oil.setColor(color);
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
                    createTime.setCreator(dao.getWorkerById(creatorId));
                } else {
                    createTime.setCreator(worker);
                }
                oil.setCreator(worker);
                dao.save(createTime);
                dao.save(oil);
                updateUtil.onSave(dao.getVROTurnByTurn(targetTurn.getTurn()));
                if (currentTurn != null && currentTurn.getId() != targetTurn.getId()){
                    updateUtil.onSave(dao.getVROTurnByTurn(currentTurn.getTurn()));
                }
                Notificator notificator = BotFactory.getNotificator();
                if (notificator != null){
                    notificator.show(oil);
                }
            }

            write(resp, SUCCESS_ANSWER);
        } else {
            write(resp, EMPTY_BODY);
        }
    }
}
