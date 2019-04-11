package api.weight;

import api.IAPI;
import constants.Branches;
import constants.Constants;
import entity.documents.LoadPlan;
import entity.transport.ActionTime;
import entity.weight.Weight;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import utils.PostUtil;
import utils.TransportUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by szpt_user045 on 22.03.2019.
 */
@WebServlet(Branches.API.SAVE_WEIGHT)
public class SaveWeightAPI extends IAPI {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = PostUtil.parseBodyJson(req);
        System.out.println(body);
        long planId = (long) body.get(Constants.ID);

        JSONArray array = (JSONArray) body.get(Constants.WEIGHTS);

        LoadPlan plan = hibernator.get(LoadPlan.class, "id", planId);
        HashMap<Long, Weight> weights = new HashMap<>();
        for (Weight w : plan.getTransportation().getWeights()){
            weights.put((long) w.getId(), w);
        }

        for (Object o : array){
            JSONObject w = (JSONObject) o;
            long id = (long) w.get(Constants.ID);
            float brutto = Float.parseFloat(String.valueOf( w.get(Constants.Weight.BRUTTO )));
            float tara = Float.parseFloat(String.valueOf(w.get(Constants.Weight.TARA)));

            Weight weight;
            boolean saveIt = false;
            if (weights.containsKey(id)){
                 weight = weights.remove(id);
            } else {
                weight = new Weight();
                weight.setTransportation(plan.getTransportation());
                saveIt = true;
            }
            changeWeight(weight, brutto, tara, req, saveIt);
        }

        hibernator.remove(weights.values().toArray());

        plan = hibernator.get(LoadPlan.class, "id", planId);

        float done = 0;
        for (Weight weight : plan.getTransportation().getWeights()){
            done += weight.getNetto();
        }

        plan.getDeal().setDone(done);
        plan.getDeal().setArchive(done >= plan.getDeal().getQuantity());

        hibernator.save(plan.getDeal());

        boolean archive = plan.getTransportation().isArchive();
        TransportUtil.checkTransport(plan.getTransportation());
        if (archive != plan.getTransportation().isArchive()) {
            hibernator.save(plan.getTransportation());
        }

        PostUtil.write(resp, answer);

        body.clear();
    }
    synchronized void changeWeight(Weight weight, float brutto, float tara, HttpServletRequest req, boolean saveIt){
        if (brutto != 0){
            ActionTime bruttoTime = weight.getBruttoTime();
            if (bruttoTime == null){
                bruttoTime = new ActionTime();
                weight.setBruttoTime(bruttoTime);
            }
            weight.setBrutto(brutto);
            bruttoTime.setTime(new Timestamp(System.currentTimeMillis()));
            bruttoTime.setCreator(getWorker(req));
            saveIt = true;
        } else if (weight.getBrutto() != 0){
            weight.setBrutto(0);
            hibernator.remove(weight.getBruttoTime());
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
            taraTime.setCreator(getWorker(req));
            saveIt = true;
        } else if (weight.getTara() != 0){
            hibernator.remove(weight.getTaraTime());
            weight.setTaraTime(null);
            saveIt = true;
        }

        if(saveIt){
            hibernator.save(weight.getBruttoTime(), weight.getTaraTime(), weight);
        }
    }
}
