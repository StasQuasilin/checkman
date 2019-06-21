package api.weight;

import api.API;
import bot.BotFactory;
import bot.Notificator;
import constants.Branches;
import constants.Constants;
import entity.Worker;
import entity.documents.LoadPlan;
import entity.log.comparators.TransportationComparator;
import entity.log.comparators.WeightComparator;
import entity.transport.ActionTime;
import entity.weight.Weight;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.DocumentUIDGenerator;
import utils.TransportUtil;
import utils.WeightUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by szpt_user045 on 22.03.2019.
 */
@WebServlet(Branches.API.SAVE_WEIGHT)
public class EditWeightAPI extends API {

    private final WeightComparator comparator = new WeightComparator();
    private final TransportationComparator transportationComparator = new TransportationComparator();
    private final Logger log = Logger.getLogger(EditWeightAPI.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if(body != null) {
            log.info(body);

            long planId = (long) body.get(Constants.ID);
            LoadPlan plan = hibernator.get(LoadPlan.class, "id", planId);
            Weight weight = plan.getTransportation().getWeight();
            boolean saveIt = false;

            if (weight == null) {
                weight = new Weight();
                weight.setUid(DocumentUIDGenerator.generateUID());
                plan.getTransportation().setWeight(weight);
                saveIt = true;
            }

            comparator.fix(weight);

            JSONObject w = (JSONObject) body.get("weight");
            float brutto = Float.parseFloat(String.valueOf(w.get(Constants.Weight.BRUTTO)));
            float tara = Float.parseFloat(String.valueOf(w.get(Constants.Weight.TARA)));

            Worker worker = getWorker(req);
            saveIt = changeWeight(weight, brutto, tara, worker, saveIt);

            if (saveIt){
                comparator.compare(weight, worker);
                hibernator.save(plan.getTransportation());
                Notificator notificator = BotFactory.getNotificator();
                if (notificator != null) {
                    notificator.weightShow(plan, weight);
                }
                WeightUtil.calculateDealDone(plan.getDeal());
                TransportUtil.calculateWeight(plan.getTransportation());

                transportationComparator.fix(plan.getTransportation());
                TransportUtil.checkTransport(plan.getTransportation());
                transportationComparator.compare(plan.getTransportation(), getWorker(req));
            }

            write(resp, answer);
            body.clear();
        } else {
            write(resp, emptyBody);
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
            if (weight.getBruttoTime() != null) {
                hibernator.remove(weight.getBruttoTime());
            }
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
            if (weight.getTaraTime() != null) {
                hibernator.remove(weight.getTaraTime());
            }
            weight.setTaraTime(null);
            saveIt = true;
        }

        if(saveIt){
            if (weight.getBruttoTime() != null) {
                hibernator.save(weight.getBruttoTime());
            }
            if (weight.getTaraTime() != null) {
                hibernator.save(weight.getTaraTime());
            }
            hibernator.save(weight);
        }

        return saveIt;
    }
}
