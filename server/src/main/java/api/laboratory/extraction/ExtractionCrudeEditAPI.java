package api.laboratory.extraction;

import api.ServletAPI;
import bot.TelegramBotFactory;
import bot.TelegramNotificator;
import constants.Branches;
import constants.Constants;
import entity.Worker;
import entity.laboratory.subdivisions.extraction.ExtractionCrude;
import entity.laboratory.subdivisions.extraction.ExtractionTurn;
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
import java.time.LocalTime;

/**
 * Created by szpt_user045 on 04.04.2019.
 */
@WebServlet(Branches.API.EXTRACTION_CRUDE_EDIT)
public class ExtractionCrudeEditAPI extends ServletAPI {

    final UpdateUtil updateUtil = new UpdateUtil();
    private final Logger log = Logger.getLogger(ExtractionCrudeEditAPI.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            log.info(body);
            ExtractionCrude crude;
            boolean save = false;
            final LocalTime time = LocalTime.parse(String.valueOf(body.get("time")));
            final LocalDate date = LocalDate.parse(String.valueOf(body.get("date")));
            final LocalDateTime localDateTime = LocalDateTime.of(date, time);
            final TurnDateTime turnDate = TurnBox.getTurnDate(localDateTime);

            if (body.containsKey(ID)) {
                crude = dao.getObjectById(ExtractionCrude.class, body.get(ID));
            } else {
                crude = new ExtractionCrude();
            }

            ExtractionTurn targetTurn = TurnService.getExtractionTurn(turnDate);
            ExtractionTurn currentTurn = crude.getTurn();
            if (currentTurn == null || currentTurn.getId() != targetTurn.getId()){
                crude.setTurn(targetTurn);
                save = true;
            }

            Timestamp timestamp = Timestamp.valueOf(localDateTime);
            if (crude.getTime() == null || !crude.getTime().equals(timestamp)) {
                crude.setTime(timestamp);
                save = true;
            }

            float humidityIncome = Float.parseFloat(String.valueOf(body.get("humidityIncome")));
            if (crude.getHumidityIncome() != humidityIncome) {
                crude.setHumidityIncome(humidityIncome);
                save = true;
            }

            float oilinessIncome = Float.parseFloat(String.valueOf(body.get("oilinessIncome")));
            if (crude.getOilinessIncome() != oilinessIncome){
                crude.setOilinessIncome(oilinessIncome);
                save = true;
            }

            float fraction = Float.parseFloat(String.valueOf(body.get("fraction")));
            if (crude.getFraction() != fraction) {
                crude.setFraction(fraction);
                save = true;
            }

            float miscellas = Float.parseFloat(String.valueOf(body.get("miscellas")));
            if (crude.getMiscellas() != miscellas) {
                crude.setMiscellas(miscellas);
                save = true;
            }

            float humidity = Float.parseFloat(String.valueOf(body.get("humidity")));
            if (crude.getHumidity() != humidity) {
                crude.setHumidity(humidity);
                save = true;
            }

            float dissolvent = Float.parseFloat(String.valueOf(body.get("dissolvent")));
            if (crude.getDissolvent() != dissolvent) {
                crude.setDissolvent(dissolvent);
                save = true;
            }

            float grease = Float.parseFloat(String.valueOf(body.get("grease")));
            if (crude.getGrease() != grease) {
                crude.setGrease(grease);
                save = true;
            }

            float explosionTemperature = Float.parseFloat(String.valueOf(body.get("explosionTemperature")));
            if (crude.getExplosionTemperature() != explosionTemperature){
                crude.setExplosionTemperature(explosionTemperature);
                save = true;
            }

            float oilHumidity = Float.parseFloat(String.valueOf(body.get("oilHumidity")));
            if (crude.getOilHumidity() != oilHumidity){
                crude.setOilHumidity(oilHumidity);
                save = true;
            }

            write(resp, SUCCESS_ANSWER);

            if (save) {
                ActionTime createTime = crude.getCreateTime();
                if (createTime == null) {
                    createTime = new ActionTime();
                    crude.setCreateTime(createTime);

                }
                createTime.setTime(new Timestamp(System.currentTimeMillis()));
                dao.save(createTime);
                dao.save(crude);

                updateUtil.onSave(dao.getExtractionTurnByTurn(targetTurn.getTurn()));
                if (currentTurn != null && targetTurn.getId() != currentTurn.getId()){
                    updateUtil.onSave(dao.getExtractionTurnByTurn(currentTurn.getTurn()));
                }
                TelegramNotificator notificator = TelegramBotFactory.getTelegramNotificator();
                if (notificator != null) {
                    notificator.extractionShow(crude);
                }
            }
        } else {
            write(resp, EMPTY_BODY);
        }
    }
}
