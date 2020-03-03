package api.weight;

import api.ServletAPI;
import bot.TelegramBotFactory;
import bot.TelegramNotificator;
import constants.Branches;
import entity.Worker;
import entity.log.comparators.TransportComparator;
import entity.log.comparators.WeightComparator;
import entity.transport.ActionTime;
import entity.transport.TransportStorageUsed;
import entity.transport.TransportUtil;
import entity.transport.Transportation;
import entity.weight.Weight;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import utils.DocumentUIDGenerator;
import utils.UpdateUtil;
import utils.WeightUtil;
import utils.storages.StorageUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by szpt_user045 on 22.03.2019.
 */
@WebServlet(Branches.API.SAVE_WEIGHT)
public class WeightEditServletAPI extends ServletAPI {

    private final WeightComparator comparator = new WeightComparator();
    private final TransportComparator transportComparator = new TransportComparator();
    private final Logger log = Logger.getLogger(WeightEditServletAPI.class);
    private final StorageUtil storageUtil = new StorageUtil();
    private final UpdateUtil updateUtil = new UpdateUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if(body != null) {
            log.info(body);
            boolean saveIt = false;

            Transportation transportation = dao.getObjectById(Transportation.class, body.get(ID));
            Weight weight = transportation.getWeight();

            if (weight == null) {
                weight = new Weight();
                weight.setUid(DocumentUIDGenerator.generateUID());
                transportation.setWeight(weight);
                saveIt = true;
            }

            comparator.fix(weight);

            JSONObject w = (JSONObject) body.get(WEIGHT);
            float brutto = Float.parseFloat(String.valueOf(w.get(BRUTTO)));
            float tara = Float.parseFloat(String.valueOf(w.get(TARA)));

            Worker worker = getWorker(req);
            saveIt = changeWeight(weight, brutto, tara, worker, saveIt);

            if (saveIt){
                if (weight.getBrutto() > 0 || weight.getTara() > 0){
                    if (transportation.getTimeIn() == null){
                        ActionTime actionTime = new ActionTime(worker);
                        dao.save(actionTime);
                        transportation.setTimeIn(actionTime);
                    }
                }
                if (weight.getNetto() > 0){
                    List<TransportStorageUsed> u = dao.getUsedStoragesByTransportation(transportation);
                    if (u.size() == 0){
                        log.info("Create storage entry");
                        TransportStorageUsed used = new TransportStorageUsed();
                        used.setAmount(1f * Math.round(weight.getNetto() * 100) / 100);
                        TransportUtil.updateUsedStorages(transportation, used, worker);
                    } else {
                        TransportUtil.updateUsedStorages(transportation, u, worker);
                    }
                    TransportUtil.updateUnloadStatistic(transportation);
                }
                comparator.compare(weight, worker);
                dao.saveTransportation(transportation);

                updateUtil.onSave(transportation);

                WeightUtil.calculateDealDone(transportation.getDeal());
                TransportUtil.calculateWeight(transportation);

                transportComparator.fix(transportation);
                TransportUtil.checkTransport(transportation);
                transportComparator.compare(transportation, getWorker(req));

                TelegramNotificator notificator = TelegramBotFactory.getTelegramNotificator();
                if (notificator != null) {
                    if (weight.getNetto() > 0) {
                        notificator.weightShow(transportation);
                    } else if (weight.getBrutto() > 0 || weight.getTara() > 0) {
                        notificator.transportInto(transportation);
                    }
                }
            }

            write(resp, SUCCESS_ANSWER);
            body.clear();
        } else {
            write(resp, EMPTY_BODY);
        }
    }
    synchronized boolean changeWeight(Weight weight, float brutto, float tara, Worker worker, boolean saveIt){
        if (brutto != 0){
            ActionTime bruttoTime = weight.getBruttoTime();
            if (bruttoTime == null){
                bruttoTime = new ActionTime();
                weight.setBruttoTime(bruttoTime);
            }
            weight.setBrutto(brutto);
            bruttoTime.setTime(new Timestamp(System.currentTimeMillis()));
            bruttoTime.setCreator(worker);
            saveIt = true;
        } else if (weight.getBrutto() != 0){
            weight.setBrutto(0);
            weight.setBruttoTime(null);
            saveIt = true;
        }

        if (tara != 0){
            ActionTime taraTime = weight.getTaraTime();
            if (taraTime == null){
                taraTime = new ActionTime();
                weight.setTaraTime(taraTime);
            }
            weight.setTara(tara);
            taraTime.setTime(new Timestamp(System.currentTimeMillis()));
            taraTime.setCreator(worker);
            saveIt = true;
        } else if (weight.getTara() != 0){
            weight.setTara(0);
            weight.setTaraTime(null);
            saveIt = true;
        }

        if(saveIt){
            if (weight.getBruttoTime() != null) {
                dao.save(weight.getBruttoTime());
            }
            if (weight.getTaraTime() != null) {
                dao.save(weight.getTaraTime());
            }
            dao.saveWeight(weight);
        }

        if (weight.getTara() == 0){
            if (weight.getTaraTime() != null) {
                dao.remove(weight.getTaraTime());
            }
        }

        if(weight.getBrutto() == 0){
            if (weight.getBruttoTime() != null){
                dao.remove(weight.getBruttoTime());
            }
        }

        return saveIt;
    }
}
