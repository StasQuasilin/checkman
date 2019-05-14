package api.laboratory.extraction;

import api.IAPI;
import bot.BotFactory;
import constants.Branches;
import constants.Constants;
import entity.Worker;
import entity.laboratory.subdivisions.extraction.ExtractionCrude;
import entity.laboratory.subdivisions.extraction.ExtractionTurn;
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
 * Created by szpt_user045 on 04.04.2019.
 */
@WebServlet(Branches.API.EXTRACTION_CRUDE_EDIT)
public class ExtractionCrudeEditAPI extends IAPI {


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            ExtractionCrude crude;
            boolean save = false;
            LocalTime time = LocalTime.parse(String.valueOf(body.get("time")));
            LocalDate date = LocalDate.parse(String.valueOf(body.get("date")));
            LocalDateTime localDateTime = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), time.getHour(), time.getMinute());
            TurnBox.TurnDateTime turnDate = TurnBox.getBox().getTurnDate(localDateTime);

            if (body.containsKey(Constants.ID)) {
                long id = (long) body.get(Constants.ID);
                crude = hibernator.get(ExtractionCrude.class, "id", id);
            } else {
                crude = new ExtractionCrude();
                System.out.println("Get Extraction turn " + Timestamp.valueOf(localDateTime));
                ExtractionTurn turn = hibernator.get(ExtractionTurn.class, "date", Timestamp.valueOf(turnDate.getDate()));
                if (turn == null) {
                    turn = new ExtractionTurn();
                    turn.setNumber(turnDate.getTurnNumber());
                    turn.setDate(Timestamp.valueOf(turnDate.getDate()));
                    hibernator.save(turn);
                }
                crude.setTurn(turn);
                save = true;
            }

            crude.setTime(Timestamp.valueOf(localDateTime));

            float humidityIncome = Float.parseFloat(String.valueOf(body.get("humidityIncome")));
            if (crude.getHumidityIncome() != humidityIncome) {
                crude.setHumidityIncome(humidityIncome);
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
                BotFactory.getNotificator().extractionShow(crude);
            }

            write(resp, answer);
        } else {
            write(resp, emptyBody);
        }
    }
}
