package api.laboratory.vro;

import api.IAPI;
import bot.BotFactory;
import constants.Branches;
import constants.Constants;
import entity.Worker;
import entity.laboratory.subdivisions.vro.ForpressCake;
import entity.laboratory.subdivisions.vro.VROCrude;
import entity.laboratory.subdivisions.vro.VROTurn;
import entity.production.Forpress;
import entity.transport.ActionTime;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.PostUtil;
import utils.TurnBox;
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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by szpt_user045 on 10.04.2019.
 */
@WebServlet(Branches.API.VRO_CRUDE_EDIT)
public class VROCrudeEditAPI extends IAPI {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            System.out.println(body);
            VROCrude crude;
            boolean save = false;
            LocalTime time = LocalTime.parse(String.valueOf(body.get("time")));
            LocalDate date = LocalDate.parse(String.valueOf(body.get("date")));
            LocalDateTime localDateTime = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), time.getHour(), time.getMinute());
            TurnBox.TurnDateTime turnDate = TurnBox.getBox().getTurnDate(localDateTime);

            if (body.containsKey(Constants.ID)) {
                long id = (long) body.get(Constants.ID);
                crude = hibernator.get(VROCrude.class, "id", id);
            } else {
                crude = new VROCrude();
                System.out.println("Get Extraction turn " + Timestamp.valueOf(localDateTime));
            }

            VROTurn turn = VROTurnService.getTurn(turnDate);

            crude.setTurn(turn);
            crude.setTime(Timestamp.valueOf(localDateTime));

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
                    createTime.setCreator(hibernator.get(Worker.class, "id", creatorId));
                } else {
                    createTime.setCreator(worker);
                }
                crude.setCreator(worker);
                hibernator.save(createTime, crude);

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
                forpressCake.setForpress(hibernator.get(Forpress.class, "id", fp.get("forpress")));
                forpressCake.setHumidity(Float.parseFloat(String.valueOf(fp.get("humidity"))));
                forpressCake.setOiliness(Float.parseFloat(String.valueOf(fp.get("oiliness"))));
                hibernator.save(forpressCake);
                cakes.add(forpressCake);
            }

            BotFactory.getNotificator().vroShow(crude, cakes);

            forpressCakeHashMap.values().forEach(hibernator::remove);

            write(resp, answer);
            cakes.clear();
            forpressCakeHashMap.clear();
        } else {
            write(resp, emptyBody);
        }
    }
}
