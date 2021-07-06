package api.weight;

import api.ServletAPI;
import constants.Branches;
import entity.Worker;
import entity.documents.DealProduct;
import entity.log.comparators.TransportComparator;
import entity.log.comparators.WeightComparator;
import entity.transport.*;
import entity.weight.Weight;
import org.apache.log4j.Logger;
import utils.DocumentUIDGenerator;
import utils.UpdateUtil;
import utils.WeightUtil;
import utils.hibernate.dao.TransportationDAO;
import utils.json.JsonObject;
import utils.notifications.Notificator;
import utils.storages.StorageUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.LinkedList;
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
    private final TransportationDAO transportationDAO = new TransportationDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JsonObject body = parseBodyGood(req);
        if(body != null) {
            Worker worker = getWorker(req);
            final LinkedList<DealProduct> dealProducts = new LinkedList<>();
            final LinkedList<Transportation> incomeTransportations = new LinkedList<>();
            final LinkedList<Transportation> allTransportations = new LinkedList<>();

            for(Object o : body.getArray(WEIGHTS)){
                JsonObject json = new JsonObject(o);
                final TransportationProduct transportationProduct = transportationDAO.getTransportationProduct(json.get(ID));
                Weight weight = transportationProduct.getWeight();

                if (weight == null){
                    weight = new Weight();
                    weight.setUid(DocumentUIDGenerator.generateUID());
                }
                comparator.fix(weight);
                boolean saveIt = false;
                final float gross = json.getFloat(GROSS);

                if (weight.getBrutto() != gross){
                    weight.setBrutto(gross);
                    final ActionTime bruttoTime = weight.getBruttoTime();
                    weight.setBruttoTime(changeWeight(gross, weight.getBrutto(), worker, bruttoTime));
                    saveIt = true;
                }

                final float tare = json.getFloat(TARE);

                if (weight.getTara() != tare){
                    weight.setTara(tare);
                    final ActionTime taraTime = weight.getTaraTime();
                    weight.setTaraTime(changeWeight(tare, weight.getTara(), worker, taraTime));
                    saveIt = true;
                }

                if (saveIt){
                    TransportUtil.weightCorrection(transportationProduct);
                    dao.save(weight);
                    write(resp, SUCCESS_ANSWER);

                    if (transportationProduct.getWeight() == null){
                        transportationProduct.setWeight(weight);
                        transportationDAO.saveProduct(transportationProduct);
                    }

                    final Transportation transportation = transportationProduct.getTransportation();
                    if (!allTransportations.contains(transportation)){
                        allTransportations.add(transportation);
                    }

                    final float net = weight.getNetto();
                    if (net > 0){
                        Notificator.weightShow(transportationProduct);
                    } else if (gross > 0 || tare > 0){
                        if (!incomeTransportations.contains(transportation)) {
                            incomeTransportations.add(transportation);
                        }
                        if (transportation.getTimeIn() == null){
                            ActionTime actionTime = new ActionTime(worker);
                            dao.save(actionTime);
                            transportation.setTimeIn(actionTime);
                        }
                    }

                    transportationDAO.saveTransportation(transportation, false);
                    updateUtil.onSave(transportation);

                    List<TransportStorageUsed> u = dao.getUsedStoragesByTransportation(transportation);
                    if (u.size() == 0){
                        TransportStorageUsed used = new TransportStorageUsed();
                        used.setAmount(1f * Math.round(weight.getNetto() * 100) / 100);
//                        TransportUtil.updateUsedStorages(transportation, used, worker);
                    } else {
//                        TransportUtil.updateUsedStorages(transportation, u, worker);
                    }
//                    TransportUtil.updateUnloadStatistic(transportation);
                    dealProducts.add(transportationProduct.getDealProduct());
                } else {
                    write(resp, SUCCESS_ANSWER);
                }

                comparator.compare(weight, worker);
            }
            for (Transportation transportation : incomeTransportations){
                Notificator.timeIn(transportation);
            }
            for (Transportation transportation : allTransportations){
                TransportUtil.checkTransport(transportation);
            }

            for (DealProduct product : dealProducts){
                WeightUtil.calculateDealDone(product);
            }
        } else {
            write(resp, EMPTY_BODY);
        }
    }

    private ActionTime changeWeight(float w1, float w2, Worker worker, ActionTime taraTime) {
        if (w1 > 0 && w1 != w2){
            if (taraTime == null){
                taraTime = new ActionTime(worker);
            } else {
                taraTime.setTime(Timestamp.valueOf(LocalDateTime.now()));
                taraTime.setCreator(worker);
            }
            return taraTime;
        }
        return null;
    }
}
