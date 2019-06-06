package api.laboratory.storages;

import api.IAPI;
import bot.BotFactory;
import bot.Notificator;
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

/**
 * Created by szpt_user045 on 06.06.2019.
 */
@WebServlet(Branches.API.LABORATORY_STORAGE_EDIT)
public class StorageEditAPI extends IAPI{

    final TurnBox turnBox = TurnBox.getBox();

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
            if (id != -1){
                analyses = hibernator.get(StorageAnalyses.class, "id", id);
            } else {
                LocalDateTime dateTime = LocalDateTime.now();
                Turn turn = TurnService.getTurn(turnBox.getTurnDate(dateTime));
                StorageTurn storageTurn = hibernator.get(StorageTurn.class, "turn", turn);
                if (storageTurn == null) {
                    storageTurn = new StorageTurn();
                    storageTurn.setTurn(turn);
                }
                
                analyses = new StorageAnalyses();
                analyses.setTurn(storageTurn);
                analyses.setOilAnalyses(new OilAnalyses());
                save = true;
            }

            Storage storage = hibernator.get(Storage.class, "id", body.get("storage"));
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

            int color = Integer.parseInt(String.valueOf(body.get("color")));
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
                    createTime.setCreator(hibernator.get(Worker.class, "id", creatorId));
                } else {
                    createTime.setCreator(worker);
                }
                oilAnalyses.setCreator(worker);

                hibernator.save(createTime, oilAnalyses, analyses.getTurn(), analyses);
                Notificator notificator = BotFactory.getNotificator();
                if (notificator != null) {
                    notificator.storagesShow(oilAnalyses);
                }
                write(resp, answer);
            }

        } else {
            write(resp, emptyBody);
        }
    }
}
