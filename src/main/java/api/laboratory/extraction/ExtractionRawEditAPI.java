package api.laboratory.extraction;

import api.IAPI;
import constants.Branches;
import constants.Constants;
import entity.Worker;
import entity.laboratory.subdivisions.extraction.ExtractionCrude;
import entity.laboratory.subdivisions.extraction.ExtractionRaw;
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
 * Created by quasilin on 09.04.2019.
 */
@WebServlet(Branches.API.EXTRACTION_RAW_EDIT)
public class ExtractionRawEditAPI extends IAPI {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = PostUtil.parseBodyJson(req);
        ExtractionRaw raw;
        boolean save = false;
        LocalTime time = LocalTime.parse(String.valueOf(body.get("time")));
        LocalDate date = LocalDate.parse(String.valueOf(body.get("date")));
        LocalDateTime localDateTime = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), time.getHour(), time.getMinute());
        TurnBox.TurnDateTime turnDate = TurnBox.getBox().getTurnDate(localDateTime);

        if (body.containsKey(Constants.ID)){
            long id = (long) body.get(Constants.ID);
            raw = hibernator.get(ExtractionRaw.class, "id", id);
        } else {
            raw = new ExtractionRaw();
            System.out.println("Get Extraction turn " + Timestamp.valueOf(localDateTime));
            ExtractionTurn turn = hibernator.get(ExtractionTurn.class, "date", Timestamp.valueOf(turnDate.getDate()));
            if (turn == null) {
                turn = new ExtractionTurn();
                turn.setNumber(turnDate.getTurnNumber());
                turn.setDate(Timestamp.valueOf(turnDate.getDate()));
                hibernator.save(turn);
            }
            raw.setTurn(turn);
            save = true;
        }

        raw.setTime(Timestamp.valueOf(localDateTime));

        float protein = Float.parseFloat(String.valueOf(body.get("protein")));
        if (raw.getProtein() != protein) {
            raw.setProtein(protein);
            save = true;
        }

        float cellulose = Float.parseFloat(String.valueOf(body.get("cellulose")));
        if (raw.getCellulose() != cellulose) {
            raw.setCellulose(cellulose);
            save = true;
        }

        if (save) {
            ActionTime createTime = raw.getCreateTime();
            if (createTime == null) {
                createTime = new ActionTime();
                raw.setCreateTime(createTime);
            }
            createTime.setTime(new Timestamp(System.currentTimeMillis()));
            Worker worker = getWorker(req);
            if (body.containsKey(Constants.CREATOR)){
                long creatorId = (long) body.get(Constants.CREATOR);
                createTime.setCreator(hibernator.get(Worker.class, "id", creatorId));
            } else {
                createTime.setCreator(worker);
            }
            raw.setCreator(worker);
            hibernator.save(createTime, raw);
        }

        write(resp, answer);
    }
}
