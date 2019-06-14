package api.laboratory.extraction;

import api.API;
import bot.BotFactory;
import constants.Branches;
import constants.Constants;
import entity.Worker;
import entity.laboratory.subdivisions.extraction.StorageProtein;
import entity.laboratory.subdivisions.extraction.ExtractionTurn;
import entity.storages.Storage;
import entity.transport.ActionTime;
import org.json.simple.JSONObject;
import utils.TurnDateTime;
import utils.turns.ExtractionTurnService;
import utils.turns.TurnBox;

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
public class ExtractionStorageProteinEdit extends API {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            StorageProtein storageProtein;
            boolean save = false;
            LocalTime time = LocalTime.parse(String.valueOf(body.get("time")));
            LocalDate date = LocalDate.parse(String.valueOf(body.get("date")));
            LocalDateTime localDateTime = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), time.getHour(), time.getMinute());
            TurnDateTime turnDate = TurnBox.getBox().getTurnDate(localDateTime);
            long id = -1;
            if (body.containsKey(Constants.ID)){
                id = (long) body.get(Constants.ID);
            }
            if (id != -1) {
                storageProtein = hibernator.get(StorageProtein.class, "id", id);
            } else {
                storageProtein = new StorageProtein();
                ExtractionTurn turn = ExtractionTurnService.getTurn(turnDate);
                storageProtein.setTurn(turn);
                save = true;
            }
            storageProtein.setTime(Timestamp.valueOf(localDateTime));

            long storageId = (long) body.get("storage");
            if (storageProtein.getStorage() == null || storageProtein.getStorage().getId() != storageId) {
                storageProtein.setStorage(hibernator.get(Storage.class, "id", storageId));
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
                    createTime.setCreator(hibernator.get(Worker.class, "id", creatorId));
                } else {
                    createTime.setCreator(worker);
                }
                storageProtein.setCreator(worker);

                hibernator.save(createTime, storageProtein);
                BotFactory.getNotificator().extractionShow(storageProtein);
            }
            write(resp, answer);
        } else {
            write(resp, emptyBody);
        }
    }
}
