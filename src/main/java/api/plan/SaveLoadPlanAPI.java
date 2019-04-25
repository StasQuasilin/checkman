package api.plan;

import api.IAPI;
import com.sun.corba.se.impl.orbutil.closure.Constant;
import constants.Branches;
import constants.Constants;
import entity.documents.Deal;
import entity.documents.LoadPlan;
import entity.transport.Driver;
import entity.transport.TransportCustomer;
import entity.transport.Transportation;
import entity.transport.Vehicle;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.DateUtil;
import utils.JsonParser;
import utils.PostUtil;
import utils.TransportUtil;
import utils.answers.SuccessAnswer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@WebServlet(Branches.API.PLAN_LIST_SAVE)
public class SaveLoadPlanAPI extends IAPI{

    final static String answer = JsonParser.toJson(new SuccessAnswer()).toJSONString();
    final Logger log = Logger.getLogger(SaveLoadPlanAPI.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        System.out.println(body);
        if(body != null) {
            long dealId = (long) body.get("dealId");
            Deal deal = hibernator.get(Deal.class, "id", dealId);
            log.info("Save load plan for deal \'" + deal.getId() + "\'...");

            HashMap<Long, LoadPlan> planHashMap = new HashMap<>();
            for (LoadPlan lp : hibernator.query(LoadPlan.class, "deal", deal)) {
                planHashMap.put((long) lp.getId(), lp);
            }

            float quantity = 0;
            for (Object o : (JSONArray) body.get("plans")) {
                JSONObject json = (JSONObject) o;

                long id = -1;
                if (json.containsKey(Constants.ID)) {
                    id = (long) json.get(Constants.ID);
                }

                LoadPlan loadPlan;
                boolean save = false;

                if (planHashMap.containsKey(id)) {
                    loadPlan = planHashMap.remove(id);
                    log.info("\tPlan \'" + loadPlan.getId() + "\'");
                } else {
                    loadPlan = new LoadPlan();
                    loadPlan.setDeal(deal);
                    loadPlan.setDocumentOrganisation(deal.getDocumentOrganisation());
                    log.info("\tNew plan");
                }

                Date date = Date.valueOf(String.valueOf(json.get(Constants.DATE)));
                log.info("\t...Date: \'" + date.toString() + "\'");
                if (loadPlan.getDate() == null || !loadPlan.getDate().equals(date)) {
                    loadPlan.setDate(date);
                    save = true;
                }

                float plan = Float.parseFloat(String.valueOf(json.get(Constants.PLAN)));
                quantity += plan;
                log.info("\t...Plan: \'" + plan + "\'");
                if (loadPlan.getPlan() != plan) {
                    loadPlan.setPlan(plan);
                    save = true;
                }

                TransportCustomer customer = TransportCustomer.valueOf(String.valueOf(json.get(Constants.CUSTOMER)));
                log.info("\t...Customer: \'" + customer.toString() + "\'");
                if (loadPlan.getCustomer() != customer) {
                    loadPlan.setCustomer(customer);
                    save = true;
                }

                Transportation transportation;
                if (loadPlan.getTransportation() == null) {
                    transportation = new Transportation();
                    transportation.setCreator(getWorker(req));
                    loadPlan.setTransportation(transportation);
                    save = true;
                } else {
                    transportation = loadPlan.getTransportation();
                }

                transportation.setDocumentOrganisation(loadPlan.getDocumentOrganisation());

                long vehicleId = -1;
                if (json.containsKey("vehicle")) {
                    vehicleId = (long) json.get("vehicle");
                }

                if (vehicleId != -1) {
                    log.info("\t...Vehicle: \'" + vehicleId + "\'");
                    transportation.setVehicle(hibernator.get(Vehicle.class, "id", vehicleId));
                    save = true;
                } else if (transportation.getVehicle() != null) {
                    transportation.setVehicle(null);
                    save = true;
                }

                long driverId = -1;
                if (json.containsKey("driver")) {
                    driverId = (long) json.get("driver");
                }

                if (driverId != -1) {
                    log.info("\t...Driver: \'" + driverId + "\'");
                    transportation.setDriver(hibernator.get(Driver.class, "id", driverId));
                    save = true;
                } else if (transportation.getDriver() != null){
                    transportation.setDriver(null);
                    save = true;
                }

                if (save) {
                    hibernator.save(transportation, loadPlan);
                }
            }

            for (LoadPlan loadPlan : planHashMap.values()) {
                if (!loadPlan.getTransportation().isArchive()) {
                    if (loadPlan.getTransportation().anyAction()) {
                        loadPlan.setCanceled(true);
                        hibernator.save(loadPlan);
                    } else {
                        hibernator.remove(loadPlan, loadPlan.getTransportation());
                    }
                }
            }

            if (quantity > deal.getQuantity()){
                deal.setQuantity(quantity);
                hibernator.save(deal);
            }

            write(resp, answer);
            body.clear();
            planHashMap.clear();
        } else {
            write(resp, emptyBody);
        }

    }
}
