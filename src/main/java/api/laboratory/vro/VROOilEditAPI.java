package api.laboratory.vro;

import api.API;
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
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;
import utils.turns.TurnBox;
import utils.turns.VROTurnService;

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
public class VROOilEditAPI extends API {

    private final Logger log = Logger.getLogger(VROOilEditAPI.class);
    private final TurnBox turnBox = TurnBox.getBox();
    dbDAO dao = dbDAOService.getDAO();

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
            TurnSettings turn = turnBox.getTurn(turnId);
            if (turn.getBegin().after(turn.getEnd())) {
                date = date.minusDays(1);
            }
            LocalDateTime turnTime = LocalDateTime.of(date, turn.getBegin().toLocalTime());
            TurnDateTime turnDate = turnBox.getTurnDate(turnTime);

            VROTurn targetTurn = VROTurnService.getTurn(turnDate);
            VROTurn currentTurn = oil.getTurn();
            boolean save = false;
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
                    createTime.setCreator(dao.getWorkerById(creatorId));
                } else {
                    createTime.setCreator(worker);
                }
                oil.setCreator(worker);
                dao.save(createTime);
                dao.save(oil);
            }

            write(resp, answer);
        } else {
            write(resp, emptyBody);
        }
    }
}
