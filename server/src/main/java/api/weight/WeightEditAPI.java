package api.weight;

import api.ServletAPI;
import bot.TelegramBotFactory;
import bot.TelegramNotificator;
import constants.Branches;
import entity.Worker;
import entity.log.comparators.TransportComparator;
import entity.log.comparators.WeightComparator;
import entity.transport.*;
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
public class WeightEditAPI extends ServletAPI {

    private final WeightComparator comparator = new WeightComparator();
    private final TransportComparator transportComparator = new TransportComparator();
    private final Logger log = Logger.getLogger(WeightEditAPI.class);
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
            float gross = Float.parseFloat(String.valueOf(w.get(BRUTTO)));
            float tare = Float.parseFloat(String.valueOf(w.get(TARA)));

            Worker worker = getWorker(req);
            saveIt = changeWeight(weight, gross, tare, worker, saveIt);

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
                write(resp, SUCCESS_ANSWER);

                TransportUtil.calculateWeight(transportation);
                WeightUtil.calculateDealDone(transportation.getDeal());

                transportComparator.fix(transportation);
                TransportUtil.checkTransport(transportation);
                transportComparator.compare(transportation, getWorker(req));

                TelegramNotificator notificator = TelegramBotFactory.getTelegramNotificator();
                if (notificator != null) {
                    final float net = weight.getNetto();
                    StringBuilder builder = new StringBuilder();
                    builder.append("Net weight of ").append(transportation.getCounterparty().getValue());
                    final Driver driver = transportation.getDriver();
                    if (driver !=null){
                        builder.append("\t").append("Driver: ").append(driver.toString());
                    }
                    builder.append(": ").append(net);
                    log.info(builder.toString());
                    if (net > 0) {
                        notificator.weightShow(transportation);
                    } else if (weight.getBrutto() > 0 || weight.getTara() > 0) {
                        notificator.transportInto(transportation);
                    }

                } else {
                    log.warn("Notificator is null");
                }
            } else {
                write(resp, SUCCESS_ANSWER);
            }
            body.clear();
        } else {
            write(resp, EMPTY_BODY);
        }
    }
    synchronized boolean changeWeight(Weight weight, float gross, float tare, Worker worker, boolean saveIt){
        if (gross != 0 && gross != weight.getBrutto()){
            ActionTime grossTime = weight.getBruttoTime();
            if (grossTime == null){
                grossTime = new ActionTime();
                weight.setBruttoTime(grossTime);
            }
            weight.setBrutto(gross);
            grossTime.setTime(new Timestamp(System.currentTimeMillis()));
            grossTime.setCreator(worker);
            saveIt = true;
        } else if (gross == 0 && weight.getBrutto() > 0){
            weight.setBrutto(0);
            weight.setBruttoTime(null);
            saveIt = true;
        }

        if (tare != 0 && tare != weight.getTara()){
            ActionTime tareTime = weight.getTaraTime();
            if (tareTime == null){
                tareTime = new ActionTime(worker);
                weight.setTaraTime(tareTime);
            }
            weight.setTara(tare);
            tareTime.setTime(new Timestamp(System.currentTimeMillis()));
            tareTime.setCreator(worker);
            saveIt = true;
        } else if (tare == 0 && weight.getTara() > 0){
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
