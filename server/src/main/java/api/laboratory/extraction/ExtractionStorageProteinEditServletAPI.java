package api.laboratory.extraction;

import api.ServletAPI;
import bot.BotFactory;
import bot.Notificator;
import constants.Branches;
import constants.Constants;
import entity.Worker;
import entity.laboratory.subdivisions.extraction.StorageProtein;
import entity.laboratory.subdivisions.extraction.ExtractionTurn;
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
 * Created by szpt_user045 on 15.05.2019.
 */
@WebServlet(Branches.API.EXTRACTION_STORAGE_PROTEIN_EDIT)
public class ExtractionStorageProteinEditServletAPI extends ServletAPI {

    final UpdateUtil updateUtil = new UpdateUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            StorageProtein storageProtein;
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
                storageProtein = dao.getStorageProteinById(id);
            } else {
                storageProtein = new StorageProtein();

                save = true;
            }

            ExtractionTurn targetTurn = TurnService.getExtractionTurn(turnDate);
            ExtractionTurn currentTurn = storageProtein.getTurn();
            if (currentTurn == null || currentTurn.getId() != targetTurn.getId()) {
                storageProtein.setTurn(targetTurn);
                save = true;
            }

            Timestamp timestamp = Timestamp.valueOf(localDateTime);
            if (storageProtein.getTime() == null || !storageProtein.getTime().equals(timestamp)) {
                storageProtein.setTime(timestamp);
                save = true;
            }

            long storageId = (long) body.get("storage");
            if (storageProtein.getStorage() == null || storageProtein.getStorage().getId() != storageId) {
                storageProtein.setStorage(dao.getStorageById(storageId));
                save = true;
            }

            float protein = Float.parseFloat(String.valueOf(body.get("protein")));
            if (storageProtein.getProtein() != protein) {
                storageProtein.setProtein(protein);
                save = true;
            }
            
            float humidity = Float.parseFloat(String.valueOf(body.get("humidity")));
            if (storageProtein.getHumidity() != humidity) {
                storageProtein.setHumidity(humidity);
                save = true;
            }
            if (save) {
                ActionTime createTime = storageProtein.getCreateTime();
                if (createTime == null) {
                    createTime = new ActionTime();
                    storageProtein.setCreateTime(createTime);
                }
                createTime.setTime(new Timestamp(System.currentTimeMillis()));
                Worker worker = getWorker(req);
                if (body.containsKey(Constants.CREATOR)) {
                    long creatorId = (long) body.get(Constants.CREATOR);
                    createTime.setCreator(dao.getWorkerById(creatorId));
                } else {
                    createTime.setCreator(worker);
                }
                storageProtein.setCreator(worker);

                dao.save(createTime, storageProtein);

                updateUtil.onSave(dao.getExtractionTurnByTurn(targetTurn.getTurn()));
                if (currentTurn != null && currentTurn.getId() != targetTurn.getId()){
                    updateUtil.onSave(dao.getExtractionTurnByTurn(currentTurn.getTurn()));
                }
                Notificator notificator = BotFactory.getNotificator();
                if (notificator !=null) {
                    notificator.extractionShow(storageProtein);
                }

            }
            write(resp, SUCCESS_ANSWER);
        } else {
            write(resp, emptyBody);
        }
    }
}
