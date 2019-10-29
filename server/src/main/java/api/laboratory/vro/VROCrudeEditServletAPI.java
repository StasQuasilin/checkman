package api.laboratory.vro;

import api.ServletAPI;
import bot.BotFactory;
import bot.Notificator;
import constants.Branches;
import constants.Constants;
import entity.Worker;
import entity.laboratory.subdivisions.vro.ForpressCake;
import entity.laboratory.subdivisions.vro.VROCrude;
import entity.laboratory.subdivisions.vro.VROTurn;
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
 * Created by szpt_user045 on 10.04.2019.
 */
@WebServlet(Branches.API.VRO_CRUDE_EDIT)
public class VROCrudeEditServletAPI extends ServletAPI {

    private final Logger log = Logger.getLogger(VROCrudeEditServletAPI.class);
    private UpdateUtil updateUtil = new UpdateUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            log.info(body);
            VROCrude crude;
            boolean save = false;
            LocalTime time = LocalTime.parse(String.valueOf(body.get("time")));
            LocalDate date = LocalDate.parse(String.valueOf(body.get("date")));
            LocalDateTime localDateTime = LocalDateTime.of(date, time);
            TurnDateTime turnDate = TurnBox.getTurnDate(localDateTime);

            if (body.containsKey(Constants.ID)) {
                Object id = body.get(Constants.ID);
                crude = dao.getVroCrudeById(id);
            } else {
                crude = new VROCrude();
            }

            VROTurn targetTurn = TurnService.getVROTurn(turnDate);
            VROTurn currentTurn = crude.getTurn();
            if (currentTurn == null || currentTurn.getId() != targetTurn.getId()){
                crude.setTurn(targetTurn);
                save = true;
            }

            Timestamp timestamp = Timestamp.valueOf(localDateTime);
            if (crude.getTime() == null || !crude.getTime().equals(timestamp)){
                crude.setTime(timestamp);
                save = true;
            }

            JSONObject before = (JSONObject) body.get("before");
            float humidityBefore = Float.parseFloat(String.valueOf(before.get("humidity")));
            if (crude.getHumidityBefore() != humidityBefore) {
                crude.setHumidityBefore(humidityBefore);
                save = true;
            }

            float sorenessBefore = Float.parseFloat(String.valueOf(before.get("soreness")));
            if (crude.getSorenessBefore() != sorenessBefore) {
                crude.setSorenessBefore(sorenessBefore);
                save = true;
            }

            JSONObject after = (JSONObject) body.get("after");
            float humidityAfter = Float.parseFloat(String.valueOf(after.get("humidity")));
            if (crude.getHumidityAfter() != humidityAfter) {
                crude.setHumidityAfter(humidityAfter);
                save = true;
            }

            float sorenessAfter = Float.parseFloat(String.valueOf(after.get("soreness")));
            if (crude.getSorenessAfter() != sorenessAfter) {
                crude.setSorenessAfter(sorenessAfter);
                save = true;
            }

            float huskiness = Float.parseFloat(String.valueOf(body.get("huskiness")));
            if (crude.getHuskiness() != huskiness) {
                crude.setHuskiness(huskiness);
                save = true;
            }

            float kernelOffset = Float.parseFloat(String.valueOf(body.get("kernelOffset")));
            if (crude.getKernelOffset() != kernelOffset) {
                crude.setKernelOffset(kernelOffset);
                save = true;
            }

            float pulpHumidity1 = Float.parseFloat(String.valueOf(body.get("pulpHumidity1")));
            if (crude.getPulpHumidity1() != pulpHumidity1) {
                crude.setPulpHumidity1(pulpHumidity1);
                save = true;
            }

            float pulpHumidity2 = Float.parseFloat(String.valueOf(body.get("pulpHumidity2")));
            if (crude.getPulpHumidity2() != pulpHumidity2) {
                crude.setPulpHumidity2(pulpHumidity2);
                save = true;
            }

            HashMap<Long, ForpressCake> forpressCakeHashMap = new HashMap<>();
            if (crude.getForpressCakes() != null) {
                for (ForpressCake cake : crude.getForpressCakes()) {
                    forpressCakeHashMap.put((long) cake.getId(), cake);
                }
            }
            List<ForpressCake> cakes = new LinkedList<>();
            for (Object o : (JSONArray) body.get("forpressCake")) {
                JSONObject fp = (JSONObject) o;
                ForpressCake forpressCake;
                long id = -1;
                if ((fp.containsKey("id"))) {
                    id = (long) fp.get("id");
                }
                if (forpressCakeHashMap.containsKey(id)) {
                    forpressCake = forpressCakeHashMap.remove(id);
                } else {
                    forpressCake = new ForpressCake();
                }
                forpressCake.setCrude(crude);
                forpressCake.setForpress(dao.getForpressById(fp.get("forpress")));
                forpressCake.setHumidity(Float.parseFloat(String.valueOf(fp.get("humidity"))));
                forpressCake.setOiliness(Float.parseFloat(String.valueOf(fp.get("oiliness"))));
                cakes.add(forpressCake);
            }

            if (cakes.size() > 0){
                save = true;
            }

            if (save) {
                ActionTime createTime = crude.getCreateTime();
                if (createTime == null) {
                    createTime = new ActionTime();
                    crude.setCreateTime(createTime);
                }
                createTime.setTime(new Timestamp(System.currentTimeMillis()));
                Worker worker = getWorker(req);
                if (body.containsKey(Constants.CREATOR)) {
                    long creatorId = (long) body.get(Constants.CREATOR);
                    createTime.setCreator(dao.getWorkerById(creatorId));
                } else {
                    createTime.setCreator(worker);
                }
                crude.setCreator(worker);
                dao.save(createTime);
                dao.save(crude);
                cakes.forEach(dao::save);

                updateUtil.onSave(dao.getVROTurnByTurn(targetTurn.getTurn()));
                if (currentTurn != null && targetTurn.getId() != currentTurn.getId()){
                    updateUtil.onSave(dao.getVROTurnByTurn(currentTurn.getTurn()));
                }

                Notificator notificator = BotFactory.getNotificator();
                if (notificator != null) {
                    notificator.vroShow(crude, cakes);
                }

                forpressCakeHashMap.values().forEach(dao::remove);
                cakes.clear();
                forpressCakeHashMap.clear();
            }

            write(resp, SUCCESS_ANSWER);

        } else {
            write(resp, EMPTY_BODY);
        }
    }
}
