package api.weight;

import api.ServletAPI;
import bot.BotFactory;
import bot.Notificator;
import constants.Branches;
import constants.Constants;
import entity.Worker;
import entity.documents.LoadPlan;
import entity.log.comparators.TransportationComparator;
import entity.log.comparators.WeightComparator;
import entity.transport.ActionTime;
import entity.transport.Transportation;
import entity.weight.Weight;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import utils.DocumentUIDGenerator;
import entity.transport.TransportUtil;
import utils.UpdateUtil;
import utils.WeightUtil;

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
    private final UpdateUtil updateUtil = new UpdateUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if(body != null) {
            log.info(body);
            boolean saveIt = false;

            long planId = (long) body.get(Constants.ID);
            LoadPlan plan = dao.getLoadPlanById(planId);
            Transportation transportation = plan.getTransportation();
            Weight weight = transportation.getWeight();

            if (weight == null) {
                weight = new Weight();
                weight.setUid(DocumentUIDGenerator.generateUID());
                transportation.setWeight(weight);
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
                dao.saveTransportation(transportation);
                updateUtil.onSave(transportation);

                WeightUtil.calculateDealDone(plan.getDeal());
                TransportUtil.calculateWeight(transportation);

                transportationComparator.fix(transportation);
                TransportUtil.checkTransport(transportation);
                transportationComparator.compare(transportation, getWorker(req));


                Notificator notificator = BotFactory.getNotificator();
                if (notificator != null) {
                    if (weight.getNetto() > 0) {
                        notificator.weightShow(transportation);
                    } else if (weight.getBrutto() == 0 && weight.getTara() > 0){
                        notificator.showLoadTime(transportation);
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
