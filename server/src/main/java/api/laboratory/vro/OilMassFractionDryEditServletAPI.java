package api.laboratory.vro;

import api.ServletAPI;
import constants.Branches;
import constants.Constants;
import entity.Worker;
import entity.laboratory.subdivisions.vro.*;
import entity.production.TurnSettings;
import entity.transport.ActionTime;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by szpt_user045 on 20.05.2019.
 */
@WebServlet(Branches.API.OIL_MASS_FRACTION_DRY)
public class OilMassFractionDryEditServletAPI extends ServletAPI {

    private final Logger log = Logger.getLogger(OilMassFractionDryEditServletAPI.class);
    final UpdateUtil updateUtil = new UpdateUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            log.info(body);
            LocalDate date = LocalDate.parse(String.valueOf(body.get("date")));
            List<TurnSettings> turns = TurnBox.getTurns();
            LocalTime time = turns.get(turns.size() - 1).getBegin().toLocalTime();
            LocalDateTime localDateTime = LocalDateTime.of(date, time);
            TurnDateTime turnDate = TurnBox.getTurnDate(localDateTime);

            boolean save = false;

            OilMassFractionDry oilMassFraction;
            long id = -1;
            if (body.containsKey(Constants.ID)){
                id = (long) body.get(Constants.ID);
            }

            if (id != -1) {
                oilMassFraction = dao.getOilMassFractionDry(id);
            } else {
                oilMassFraction = new OilMassFractionDry();
            }

            VROTurn targetTurn = TurnService.getVROTurn(turnDate);
            VROTurn currentTurn = oilMassFraction.getTurn();
            if (currentTurn == null || currentTurn.getId() != targetTurn.getId()){
                oilMassFraction.setTurn(targetTurn);
                save = true;
            }

            float seed = Float.parseFloat(String.valueOf(body.get("seed")));
            if (oilMassFraction.getSeed() != seed) {
                oilMassFraction.setSeed(seed);
                save = true;
            }

            float husk = Float.parseFloat(String.valueOf(body.get("husk")));
            if (oilMassFraction.getHusk() != husk) {
                oilMassFraction.setHusk(husk);
                save = true;
            }

            HashMap<Long, ForpressCakeDailyDry> forpressCakes = new HashMap<>();
            if (oilMassFraction.getForpressCakes() != null) {
                for (ForpressCakeDailyDry fcd : oilMassFraction.getForpressCakes()) {
                    forpressCakes.put((long) fcd.getId(), fcd);
                }
            }
            LinkedList<ForpressCakeDailyDry> forpressList = new LinkedList<>();
            for (Object o : (JSONArray)body.get("forpress")){
                JSONObject forpress  = (JSONObject) o;
                ForpressCakeDailyDry fcd;

                long forpressCakeId = -1;
                if (forpress.containsKey(Constants.ID)){
                    forpressCakeId = (long) forpress.get(Constants.ID);
                }

                if (forpressCakeId != -1 && forpressCakes.containsKey(forpressCakeId)) {
                    fcd = forpressCakes.remove(forpressCakeId);
                } else {
                    fcd = new ForpressCakeDailyDry();
                    fcd.setMassFraction(oilMassFraction);
                }

                forpressList.add(fcd);

                long forpressId = (long) forpress.get("forpress");
                if (fcd.getForpress() == null || fcd.getForpress().getId() != forpressId) {
                    fcd.setForpress(dao.getForpressById(forpressId));
                    save = true;
                }

                float humidity = Float.parseFloat(String.valueOf(forpress.get("oiliness")));
                if (fcd.getOilcake() != humidity) {
                    fcd.setOilcake(humidity);
                    save = true;
                }
            }

            forpressCakes.values().forEach(dao::remove);
            if (forpressList.size() > 0) {
                save = true;
            }

            if (save) {
                ActionTime createTime = oilMassFraction.getCreateTime();
                if (createTime == null) {
                    createTime = new ActionTime();
                    oilMassFraction.setCreateTime(createTime);
                }
                createTime.setTime(new Timestamp(System.currentTimeMillis()));
                Worker worker = getWorker(req);
                if (body.containsKey(Constants.CREATOR)) {
                    long creatorId = (long) body.get(Constants.CREATOR);
                    createTime.setCreator(dao.getWorkerById(creatorId));
                } else {
                    createTime.setCreator(worker);
                }
                oilMassFraction.setCreator(worker);
                dao.save(createTime);
                dao.save(oilMassFraction);
                forpressList.forEach(dao::save);
                forpressList.clear();
                updateUtil.onSave(dao.getVROTurnByTurn(targetTurn.getTurn()));
                if (currentTurn != null && currentTurn.getId() != targetTurn.getId()) {
                    updateUtil.onSave(dao.getVROTurnByTurn(currentTurn.getTurn()));
                }
            }
            write(resp, answer);
        } else {
            write(resp, emptyBody);
        }

    }
}
