package api.laboratory.vro;

import api.ServletAPI;
import constants.Branches;
import constants.Constants;
import entity.Worker;
import entity.laboratory.subdivisions.vro.VRODaily;
import entity.laboratory.subdivisions.vro.VROTurn;
import entity.production.TurnSettings;
import entity.transport.ActionTime;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import utils.UpdateUtil;
import utils.turns.TurnBox;
import utils.TurnDateTime;
import utils.turns.TurnService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Created by szpt_user045 on 11.04.2019.
 */
@WebServlet(Branches.API.VRO_DAILY_EDIT)
public class VRODailyEditAPI extends ServletAPI {

    private final Logger log = Logger.getLogger(VRODailyEditAPI.class);
    final UpdateUtil updateUtil = new UpdateUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            log.info(body);
            LocalDate date = LocalDate.parse(String.valueOf(body.get("date")));
            int turnNumber = Integer.parseInt(String.valueOf(body.get("turn")));
            TurnSettings turn = TurnBox.getTurnByNumber(turnNumber);
            LocalTime time = turn.getBegin().toLocalTime();
            LocalDateTime localDateTime = LocalDateTime.of(date, time);
            TurnDateTime turnDate = TurnBox.getTurnDate(localDateTime);

            boolean save = false;

            VRODaily daily;
            if (body.containsKey(Constants.ID)) {
                daily = dao.getVroDailyById(body.get(Constants.ID));
            } else {
                daily = new VRODaily();
            }

            VROTurn targetTurn = TurnService.getVROTurn(turnDate);
            VROTurn currentTurn = daily.getTurn();
            if (currentTurn == null || currentTurn.getId() != targetTurn.getId()){
                daily.setTurn(targetTurn);
                save = true;
            }

            final float dry = Float.parseFloat(String.valueOf(body.get("dry")));
            if (daily.getDryOiliness() != dry){
                daily.setDryOiliness(dry);
                save = true;
            }

            float kernelHumidity = Float.parseFloat(String.valueOf(body.get("kernelHumidity")));
            if (daily.getKernelHumidity() != kernelHumidity) {
                daily.setKernelHumidity(kernelHumidity);
                save = true;
            }

            float huskHumidity = Float.parseFloat(String.valueOf(body.get("huskHumidity")));
            if (daily.getHuskHumidity() != huskHumidity) {
                daily.setHuskHumidity(huskHumidity);
                save = true;
            }

            float huskSoreness = Float.parseFloat(String.valueOf(body.get("huskSoreness")));
            if (daily.getHuskSoreness() != huskSoreness) {
                daily.setHuskSoreness(huskSoreness);
                save = true;
            }

            float kernelPercent = Float.parseFloat(String.valueOf(body.get("kernelPercent")));
            if (daily.getKernelPercent() != kernelPercent) {
                daily.setKernelPercent(kernelPercent);
                save = true;
            }

            float huskPercent = Float.parseFloat(String.valueOf(body.get("huskPercent")));
            if (daily.getHuskPercent() != huskPercent) {
                daily.setHuskPercent(huskPercent);
                save = true;
            }

            if (save) {
                ActionTime createTime = daily.getCreateTime();
                if (createTime == null) {
                    createTime = new ActionTime();
                    daily.setCreateTime(createTime);
                }
                createTime.setTime(new Timestamp(System.currentTimeMillis()));
                Worker worker = getWorker(req);
                if (body.containsKey(Constants.CREATOR)) {
                    long creatorId = (long) body.get(Constants.CREATOR);
                    createTime.setCreator(dao.getObjectById(Worker.class, creatorId));
                } else {
                    createTime.setCreator(worker);
                }
                dao.save(createTime, daily);
                updateUtil.onSave(dao.getVROTurnByTurn(targetTurn.getTurn()));
                if (currentTurn != null && currentTurn.getId() != targetTurn.getId()) {
                    updateUtil.onSave(dao.getVROTurnByTurn(currentTurn.getTurn()));
                }
            }

            write(resp, SUCCESS_ANSWER);
        } else {
            write(resp, EMPTY_BODY);
        }
    }
}
