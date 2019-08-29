package api.laboratory.extraction;

import api.ServletAPI;
import constants.Branches;
import constants.Constants;
import entity.Worker;
import entity.laboratory.subdivisions.extraction.ExtractionRaw;
import entity.laboratory.subdivisions.extraction.ExtractionTurn;
import entity.transport.ActionTime;
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
 * Created by quasilin on 09.04.2019.
 */
@WebServlet(Branches.API.EXTRACTION_RAW_EDIT)
public class ExtractionRawEditServletAPI extends ServletAPI {

    final UpdateUtil updateUtil = new UpdateUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            ExtractionRaw raw;
            boolean save = false;
            LocalTime time = LocalTime.parse(String.valueOf(body.get("time")));
            LocalDate date = LocalDate.parse(String.valueOf(body.get("date")));
            LocalDateTime localDateTime = LocalDateTime.of(date, time);
            TurnDateTime turnDate = TurnBox.getTurnDate(localDateTime);

            if (body.containsKey(Constants.ID)) {
                long id = (long) body.get(Constants.ID);
                raw = dao.getExtractionRawById(id);
            } else {
                raw = new ExtractionRaw();
                save = true;
            }

            ExtractionTurn targetTurn = TurnService.getExtractionTurn(turnDate);
            ExtractionTurn currentTurn = raw.getTurn();
            if(currentTurn == null || currentTurn.getId() != targetTurn.getId()){
                raw.setTurn(targetTurn);
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
                if (body.containsKey(Constants.CREATOR)) {
                    long creatorId = (long) body.get(Constants.CREATOR);
                    createTime.setCreator(dao.getWorkerById(creatorId));
                } else {
                    createTime.setCreator(worker);
                }
                raw.setCreator(worker);
                dao.save(createTime, raw);
                updateUtil.onSave(dao.getExtractionTurnByTurn(targetTurn.getTurn()));
                if (currentTurn != null && targetTurn.getId() != currentTurn.getId()){
                    updateUtil.onSave(dao.getExtractionTurnByTurn(currentTurn.getTurn()));
                }
            }

            write(resp, SUCCESS_ANSWER);
        } else {
            write(resp, emptyBody);
        }
    }
}
