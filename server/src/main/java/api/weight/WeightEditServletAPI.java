package api.weight;

import api.ServletAPI;
import bot.TelegramBotFactory;
import bot.TelegramNotificator;
import com.sun.org.apache.bcel.internal.generic.FLOAD;
import constants.Branches;
import constants.Constants;
import entity.Worker;
import entity.documents.LoadPlan;
import entity.log.comparators.TransportationComparator;
import entity.log.comparators.WeightComparator;
import entity.transport.*;
import entity.weight.Weight;
import entity.weight.Weight2;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
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

/**
 * Created by szpt_user045 on 22.03.2019.
 */
@WebServlet(Branches.API.SAVE_WEIGHT)
public class WeightEditServletAPI extends ServletAPI {

    private final WeightComparator comparator = new WeightComparator();
    private final TransportationComparator transportationComparator = new TransportationComparator();
    private final Logger log = Logger.getLogger(WeightEditServletAPI.class);
    private final StorageUtil storageUtil = new StorageUtil();
    private final UpdateUtil updateUtil = new UpdateUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if(body != null) {
            log.info(body);

            for (Object o : (JSONArray)body.get(WEIGHT)){
                JSONObject json = (JSONObject) o;
                Weight2 weight = dao.getObjectById(Weight2.class, json.get(ID));
                TransportationProduct product = dao.getObjectById(TransportationProduct.class, json.get(PRODUCT));
                if (weight == null){
                    weight = new Weight2();
                    weight.setUid(DocumentUIDGenerator.generateUID());
                } else {
                    comparator.fix(weight);
                }
                float brutto = Float.parseFloat(String.valueOf(json.get(BRUTTO)));
                float tara = Float.parseFloat(String.valueOf(json.get(TARA)));
                Worker worker = getWorker(req);
                if (changeWeight(weight, brutto, tara, worker, false)){
                    if (product.getWeight() == null){
                        product.setWeight(weight);
                        dao.save(product);
                    }
                }
                comparator.compare(weight, worker);
            }
            write(resp, SUCCESS_ANSWER);
        } else {
            write(resp, EMPTY_BODY);
        }
    }
    synchronized boolean changeWeight(Weight2 weight, float brutto, float tara, Worker worker, boolean saveIt){

        if (brutto != 0){
            if (weight.getBrutto() != brutto) {
                weight.setBrutto(brutto);
                ActionTime bruttoTime = weight.getBruttoTime();
                if (bruttoTime == null) {
                    bruttoTime = new ActionTime();
                    weight.setBruttoTime(bruttoTime);
                }
                bruttoTime.setTime(new Timestamp(System.currentTimeMillis()));
                bruttoTime.setCreator(worker);
                saveIt = true;
            }
        } else if (weight.getBrutto() != 0){
            weight.setBrutto(0);
            dao.remove(weight.getBruttoTime());
            weight.setBruttoTime(null);
            saveIt = true;
        }

        if (tara != 0){
            if (weight.getTara() != tara) {
                weight.setTara(tara);
                ActionTime taraTime = weight.getTaraTime();
                if (taraTime == null) {
                    taraTime = new ActionTime();
                    weight.setTaraTime(taraTime);
                }
                taraTime.setTime(new Timestamp(System.currentTimeMillis()));
                taraTime.setCreator(worker);
                saveIt = true;
            }
        } else if (weight.getTara() != 0){
            weight.setTara(0);
            dao.remove(weight.getTaraTime());
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
            dao.save(weight);
        }

        return saveIt;
    }
}
