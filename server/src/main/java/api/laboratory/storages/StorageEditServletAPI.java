package api.laboratory.storages;

import api.ServletAPI;
import bot.TelegramBotFactory;
import bot.TelegramNotificator;
import constants.Branches;
import constants.Constants;
import entity.Worker;
import entity.laboratory.OilAnalyses;
import entity.laboratory.storages.StorageAnalyses;
import entity.laboratory.storages.StorageTurn;
import entity.production.Turn;
import entity.storages.Storage;
import entity.transport.ActionTime;
import org.json.simple.JSONObject;
import utils.UpdateUtil;
import utils.turns.TurnBox;
import utils.turns.TurnService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Created by szpt_user045 on 06.06.2019.
 */
@WebServlet(Branches.API.LABORATORY_STORAGE_EDIT)
public class StorageEditServletAPI extends ServletAPI {

    final UpdateUtil updateUtil = new UpdateUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {

            long id = -1;
            if(body.containsKey("id")){
                id = (long) body.get("id");
            }
            StorageAnalyses analyses;
            boolean save = false;
            Timestamp timestamp = Timestamp.valueOf(String.valueOf(body.get("convert")));
            LocalDateTime dateTime = timestamp.toLocalDateTime();
            Turn turn = TurnService.getTurn(TurnBox.getTurnDate(dateTime));

            if (id != -1){
                analyses = dao.getStorageAnalysesById(id);
            } else {

                StorageTurn storageTurn = dao.getStorageTurnByTurn(turn);
                if (storageTurn == null) {
                    storageTurn = new StorageTurn();
                    storageTurn.setTurn(turn);
                }

                analyses = new StorageAnalyses();
                analyses.setTurn(storageTurn);
                analyses.setOilAnalyses(new OilAnalyses());
                save = true;
            }

            if (analyses.getDate() == null || !analyses.getDate().equals(timestamp)){
                analyses.setDate(timestamp);
                save = true;
            }

            Storage storage = dao.getStorageById(body.get("storage"));
            if (analyses.getStorage() == null || analyses.getStorage().getId() != storage.getId()){
                analyses.setStorage(storage);
                save = true;
            }
            OilAnalyses oilAnalyses = analyses.getOilAnalyses();
            float phosphorus = Float.parseFloat(String.valueOf(body.get("phosphorus")));
            if (oilAnalyses.getPhosphorus() != phosphorus) {
                oilAnalyses.setPhosphorus(phosphorus);
                save = true;
            }

            float acid = Float.parseFloat(String.valueOf(body.get("acid")));
            if (oilAnalyses.getAcidValue() != acid){
                oilAnalyses.setAcidValue(acid);
                save = true;
            }

            float peroxide = Float.parseFloat(String.valueOf(body.get("peroxide")));
            if (oilAnalyses.getPeroxideValue() != peroxide) {
                oilAnalyses.setPeroxideValue(peroxide);
                save = true;
            }

            float color = Float.parseFloat(String.valueOf(body.get("color")));
            if (oilAnalyses.getColor() != color){
                oilAnalyses.setColor(color);
                save = true;
            }

            if (save){
                ActionTime createTime = oilAnalyses.getCreateTime();
                if (createTime == null) {
                    createTime = new ActionTime();
                    oilAnalyses.setCreateTime(createTime);
                }
                createTime.setTime(new Timestamp(System.currentTimeMillis()));
                Worker worker = getWorker(req);
                if (body.containsKey(Constants.CREATOR)) {
                    long creatorId = (long) body.get(Constants.CREATOR);
                    createTime.setCreator(dao.getObjectById(creatorId));
                } else {
                    createTime.setCreator(worker);
                }

                dao.save(createTime, oilAnalyses, analyses.getTurn(), analyses);
                updateUtil.onSave(dao.getStorageTurnByTurn(turn));
                TelegramNotificator notificator = TelegramBotFactory.getTelegramNotificator();
                if (notificator != null) {
                    notificator.storagesShow(analyses);
                }
                write(resp, SUCCESS_ANSWER);
            }

        } else {
            write(resp, EMPTY_BODY);
        }
    }
}
