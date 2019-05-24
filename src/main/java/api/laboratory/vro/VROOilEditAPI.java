package api.laboratory.vro;

import api.IAPI;
import constants.Branches;
import constants.Constants;
import entity.Worker;
import entity.laboratory.subdivisions.extraction.ExtractionOIl;
import entity.laboratory.subdivisions.extraction.ExtractionTurn;
import entity.laboratory.subdivisions.vro.VROOil;
import entity.laboratory.subdivisions.vro.VROTurn;
import entity.production.Turn;
import entity.transport.ActionTime;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
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
 * Created by szpt_user045 on 11.04.2019.
 */
@WebServlet(Branches.API.VRO_OIL_EDIT)
public class VROOilEditAPI extends IAPI {

    private final Logger log = Logger.getLogger(VROOilEditAPI.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            log.info(body);
            VROOil oil;
            if (body.containsKey(Constants.ID)) {
                long id = (long) body.get(Constants.ID);
                oil = hibernator.get(VROOil.class, "id", id);
            } else {
                oil = new VROOil();
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
            VROTurn vroTurn = hibernator.get(VROTurn.class, "date", Timestamp.valueOf(turnTime));
            if (vroTurn == null) {
                vroTurn = new VROTurn();
                vroTurn.setNumber(turn.getNumber());
                vroTurn.setDate(Timestamp.valueOf(turnTime));
                hibernator.save(turn);
            }
            oil.setTurn(vroTurn);
            boolean save = false;

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

            int color = Integer.parseInt(String.valueOf(body.get("color")));
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
                    createTime.setCreator(hibernator.get(Worker.class, "id", creatorId));
                } else {
                    createTime.setCreator(worker);
                }
                oil.setCreator(worker);
                hibernator.save(createTime, oil);
            }

            write(resp, answer);
        } else {
            write(resp, emptyBody);
        }
    }
}
