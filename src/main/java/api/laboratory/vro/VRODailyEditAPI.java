package api.laboratory.vro;

import api.IAPI;
import constants.Branches;
import constants.Constants;
import entity.Worker;
import entity.laboratory.subdivisions.vro.VRODaily;
import entity.laboratory.subdivisions.vro.VROTurn;
import entity.production.TurnSettings;
import entity.transport.ActionTime;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import utils.turns.TurnBox;
import utils.TurnDateTime;
import utils.turns.VROTurnService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * Created by szpt_user045 on 11.04.2019.
 */
@WebServlet(Branches.API.VRO_DAILY_EDIT)
public class VRODailyEditAPI extends IAPI {

    private final Logger log = Logger.getLogger(VRODailyEditAPI.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            log.info(body);
            LocalDate date = LocalDate.parse(String.valueOf(body.get("date")));
            List<TurnSettings> turns = TurnBox.getBox().getTurns();
            LocalTime time = turns.get(turns.size() - 1).getBegin().toLocalTime();
            LocalDateTime localDateTime = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), time.getHour(), time.getMinute());
            TurnDateTime turnDate = TurnBox.getBox().getTurnDate(localDateTime);

            VROTurn turn = VROTurnService.getTurn(turnDate);
            boolean save = false;

            VRODaily daily;
            if (body.containsKey(Constants.ID)) {
                long id = (long) body.get(Constants.ID);
                daily = hibernator.get(VRODaily.class, "id", id);
            } else {
                daily = new VRODaily();
            }

            daily.setTurn(turn);

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
                    createTime.setCreator(hibernator.get(Worker.class, "id", creatorId));
                } else {
                    createTime.setCreator(worker);
                }
                daily.setCreator(worker);
                hibernator.save(createTime, daily);
            }

            write(resp, answer);
        } else {
            write(resp, emptyBody);
        }
    }
}
