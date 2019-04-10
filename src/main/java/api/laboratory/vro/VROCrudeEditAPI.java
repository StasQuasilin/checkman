package api.laboratory.vro;

import api.IAPI;
import constants.Branches;
import constants.Constants;
import entity.Worker;
import entity.laboratory.subdivisions.extraction.ExtractionCrude;
import entity.laboratory.subdivisions.extraction.ExtractionTurn;
import entity.laboratory.subdivisions.vro.VROCrude;
import entity.laboratory.subdivisions.vro.VROTurn;
import entity.transport.ActionTime;
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
import java.time.LocalTime;

/**
 * Created by szpt_user045 on 10.04.2019.
 */
@WebServlet(Branches.API.VRO_CRUDE_EDIT)
public class VROCrudeEditAPI extends IAPI {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = PostUtil.parseBodyJson(req);
        VROCrude crude;
        boolean save = false;
        LocalTime time = LocalTime.parse(String.valueOf(body.get("time")));
        LocalDate date = LocalDate.parse(String.valueOf(body.get("date")));
        LocalDateTime localDateTime = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), time.getHour(), time.getMinute());
        TurnBox.TurnDateTime turnDate = TurnBox.getBox().getTurnDate(localDateTime);

        if (body.containsKey(Constants.ID)){
            long id = (long) body.get(Constants.ID);
            crude = hibernator.get(VROCrude.class, "id", id);
        } else {
            crude = new VROCrude();
            System.out.println("Get Extraction turn " + Timestamp.valueOf(localDateTime));
        }

        VROTurn turn = hibernator.get(VROTurn.class, "date", Timestamp.valueOf(turnDate.getDate()));
        if (turn == null) {
            turn = new VROTurn();
            turn.setNumber(turnDate.getTurnNumber());
            turn.setDate(Timestamp.valueOf(turnDate.getDate()));
            hibernator.save(turn);
            save = true;
        }
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

        float pulpHumidity = Float.parseFloat(String.valueOf(body.get("pulpHumidity")));
        if (crude.getPulpHumidity() != pulpHumidity) {
            crude.setPulpHumidity(pulpHumidity);
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
            if (body.containsKey(Constants.CREATOR)){
                long creatorId = (long) body.get(Constants.CREATOR);
                createTime.setCreator(hibernator.get(Worker.class, "id", creatorId));
            } else {
                createTime.setCreator(worker);
            }
            crude.setCreator(worker);
            hibernator.save(createTime, crude);
        }

        write(resp, answer);
    }
}
