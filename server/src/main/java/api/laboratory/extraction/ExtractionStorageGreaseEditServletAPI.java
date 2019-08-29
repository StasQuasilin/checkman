package api.laboratory.extraction;

import api.ServletAPI;
import bot.BotFactory;
import bot.Notificator;
import constants.Branches;
import constants.Constants;
import entity.Worker;
import entity.laboratory.subdivisions.extraction.ExtractionTurn;
import entity.laboratory.subdivisions.extraction.StorageGrease;
import entity.transport.ActionTime;
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
 * Created by szpt_user045 on 16.05.2019.
 */
@WebServlet(Branches.API.EXTRACTION_STORAGE_GREASE_EDIT)
public class ExtractionStorageGreaseEditServletAPI extends ServletAPI {

    final UpdateUtil updateUtil = new UpdateUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            StorageGrease storageGrease;
            boolean save = false;
            LocalTime time = LocalTime.parse(String.valueOf(body.get("time")));
            LocalDate date = LocalDate.parse(String.valueOf(body.get("date")));
            LocalDateTime localDateTime = LocalDateTime.of(date, time);
            TurnDateTime turnDate = TurnBox.getTurnDate(localDateTime);
            long id = -1;
            if (body.containsKey(Constants.ID)){
                id = (long) body.get(Constants.ID);
            }
            if (id != -1) {
                storageGrease = dao.getStorageGreaseById(id);
            } else {
                storageGrease = new StorageGrease();
            }

            ExtractionTurn targetTurn = TurnService.getExtractionTurn(turnDate);
            ExtractionTurn currentTurn = storageGrease.getTurn();
            if (currentTurn == null || targetTurn.getId() != currentTurn.getId()){
                storageGrease.setTurn(targetTurn);
                save = true;
            }

            Timestamp timestamp = Timestamp.valueOf(localDateTime);
            if (storageGrease.getTime() == null || !storageGrease.getTime().equals(timestamp)) {
                storageGrease.setTime(timestamp);
                save = true;
            }

            long storageId = (long) body.get("storage");
            if (storageGrease.getStorage() == null || storageGrease.getStorage().getId() != storageId) {
                storageGrease.setStorage(dao.getStorageById(storageId));
                save = true;
            }

            float grease = Float.parseFloat(String.valueOf(body.get("grease")));
            if (storageGrease.getGrease() != grease) {
                storageGrease.setGrease(grease);
                save = true;
            }

            float humidity = Float.parseFloat(String.valueOf(body.get("humidity")));
            if (storageGrease.getHumidity() != humidity) {
                storageGrease.setHumidity(humidity);
                save = true;
            }
            if (save) {
                ActionTime createTime = storageGrease.getCreateTime();
                if (createTime == null) {
                    createTime = new ActionTime();
                    storageGrease.setCreateTime(createTime);
                }
                createTime.setTime(new Timestamp(System.currentTimeMillis()));
                Worker worker = getWorker(req);
                if (body.containsKey(Constants.CREATOR)) {
                    long creatorId = (long) body.get(Constants.CREATOR);
                    createTime.setCreator(dao.getWorkerById(creatorId));
                } else {
                    createTime.setCreator(worker);
                }
                storageGrease.setCreator(worker);

                dao.save(createTime, storageGrease);
                updateUtil.onSave(dao.getExtractionTurnByTurn(targetTurn.getTurn()));
                if (currentTurn != null && currentTurn.getId() != targetTurn.getId()){
                    updateUtil.onSave(dao.getExtractionTurnByTurn(currentTurn.getTurn()));
                }

                Notificator notificator = BotFactory.getNotificator();
                if (notificator != null) {
                    notificator.extractionShow(storageGrease);
                }

            }
            write(resp, SUCCESS_ANSWER);
        } else {
            write(resp, emptyBody);
        }
    }
}
