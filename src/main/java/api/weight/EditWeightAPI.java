package api.weight;

import api.IAPI;
import bot.BotFactory;
import bot.Notificator;
import constants.Branches;
import constants.Constants;
import entity.Worker;
import entity.documents.LoadPlan;
import entity.transport.ActionTime;
import entity.weight.Weight;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.PostUtil;
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
public class EditWeightAPI extends IAPI {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if(body != null) {
            System.out.println(body);
            long planId = (long) body.get(Constants.ID);

            JSONArray array = (JSONArray) body.get(Constants.WEIGHTS);

            LoadPlan plan = hibernator.get(LoadPlan.class, "id", planId);
            List<Weight> weightList = new ArrayList<>();
            HashMap<Long, Weight> weights = new HashMap<>();
            for (Weight w : plan.getTransportation().getWeights()) {
                weights.put((long) w.getId(), w);
            }

            for (Object o : array) {
                JSONObject w = (JSONObject) o;
                long id = (long) w.get(Constants.ID);
                float brutto = Float.parseFloat(String.valueOf(w.get(Constants.Weight.BRUTTO)));
                float tara = Float.parseFloat(String.valueOf(w.get(Constants.Weight.TARA)));

                Weight weight;
                boolean saveIt = false;
                if (weights.containsKey(id)) {
                    weight = weights.remove(id);
                } else {
                    weight = new Weight();
                    weight.setTransportation(plan.getTransportation());
                    saveIt = true;
                }

                changeWeight(weight, brutto, tara, getWorker(req), saveIt);
                weightList.add(weight);
            }

            hibernator.remove(weights.values().toArray());
            WeightUtil.calculateDealDone(plan.getDeal());
            TransportUtil.checkTransport(plan.getTransportation());

            write(resp, answer);

            body.clear();
        } else {
            write(resp, emptyBody);
        }
    }
    synchronized void changeWeight(Weight weight, float brutto, float tara, Worker worker, boolean saveIt){
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
    }
}
