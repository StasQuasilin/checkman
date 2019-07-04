package api.plan;

import api.API;
import constants.Branches;
import constants.Constants;
import entity.Worker;
import entity.documents.Deal;
import entity.documents.DocumentUID;
import entity.documents.LoadPlan;
import entity.log.comparators.LoadPlanComparator;
import entity.log.comparators.TransportationComparator;
import entity.transport.*;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.*;
import utils.answers.SuccessAnswer;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@WebServlet(Branches.API.PLAN_LIST_SAVE)
public class SaveLoadPlanAPI extends API {

    final Logger log = Logger.getLogger(SaveLoadPlanAPI.class);
    final LoadPlanComparator planComparator = new LoadPlanComparator();
    final TransportationComparator transportationComparator = new TransportationComparator();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);

        if(body != null) {
            log.info(body);
            long dealId = (long) body.get(Constants.DEAL_ID);
            Deal deal = dao.getDealById(dealId);
            log.info("Save load plan for deal \'" + deal.getId() + "\'...");
            Worker worker = getWorker(req);

            final HashMap<Long, LoadPlan> planHashMap = new HashMap<>();
            for (LoadPlan lp : dao.getLoadPlanByDeal(deal)) {
                planHashMap.put((long) lp.getId(), lp);
            }

            float quantity = 0;
            Date from = Date.valueOf(LocalDate.now());
            Date to = Date.valueOf(LocalDate.now());
            for (Object o : (JSONArray) body.get("plans")) {
                JSONObject json = (JSONObject) o;

                long id = -1;
                if (json.containsKey(Constants.ID)) {
                    id = (long) json.get(Constants.ID);
                }

                LoadPlan loadPlan;
                Transportation transportation;
                boolean save = false;

                if (planHashMap.containsKey(id)) {
                    loadPlan = planHashMap.remove(id);
                    transportation = loadPlan.getTransportation();
                    planComparator.fix(loadPlan);
                    transportationComparator.fix(transportation);
                    log.info("\tPlan \'" + loadPlan.getId() + "\'");
                } else {
                    loadPlan = new LoadPlan();
                    loadPlan.setUid(DocumentUIDGenerator.generateUID());
                    loadPlan.setDeal(deal);
                    loadPlan.setDocumentOrganisation(deal.getDocumentOrganisation());
                    transportation = new Transportation();
                    transportation.setUid(DocumentUIDGenerator.generateUID());
                    transportation.setCreator(getWorker(req));
                    loadPlan.setTransportation(transportation);
                    planComparator.fix(null);
                    transportationComparator.fix(null);
                    log.info("\tNew plan");
                }


                Date date = Date.valueOf(String.valueOf(json.get(Constants.DATE)));
                log.info("\t...Date: \'" + date.toString() + "\'");
                if (loadPlan.getDate() == null || !loadPlan.getDate().equals(date)) {
                    loadPlan.setDate(date);
                    save = true;
                }

                if (date.before(from)){
                    from = date;
                }

                if (date.after(to)){
                    to = date;
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

                transportation.setDocumentOrganisation(loadPlan.getDocumentOrganisation());

                long vehicleId = -1;
                if (json.containsKey("vehicle")) {
                    vehicleId = (long) json.get("vehicle");
                }

                if (vehicleId != -1) {
                    log.info("\t...Vehicle: \'" + vehicleId + "\'");
                    transportation.setVehicle(dao.getVehicleById(vehicleId));
                    save = true;
                } else if (transportation.getVehicle() != null) {
                    transportation.setVehicle(null);
                    save = true;
                }

                long driverId = -1;
                if (json.containsKey("driver")) {
                    driverId = (long)  json.get("driver");
                }

                if (driverId != -1) {
                    log.info("\t...Driver: \'" + driverId + "\'");
                    transportation.setDriver(dao.getDriverByID(driverId));
                    save = true;
                } else if (transportation.getDriver() != null){
                    transportation.setDriver(null);
                    save = true;
                }

                if (save) {
                    dao.save(transportation, loadPlan);
                }

                final HashSet<TransportationNote> notes = new HashSet<>();
                notes.addAll(dao.getTransportationNotesByTransportation(transportation));
                for (Object n : (JSONArray)json.get("notes")){
                    JSONObject nj = (JSONObject) n;
                    Object noteId = null;
                    if (nj.containsKey(Constants.ID)){
                        noteId = nj.get(Constants.ID);
                    }
                    TransportationNote note = null;
                    if (noteId != null) {
                        note = dao.getTransportationNotesById(noteId);
                    }
                    if (note == null){
                        note = new TransportationNote();
                        note.setTransportation(transportation);
                        note.setTime(new Timestamp(System.currentTimeMillis()));
                        note.setCreator(worker);
                    }
                    if (notes.contains(note)){
                        notes.remove(note);
                    }
                    String noteText = (String) nj.get("note");
                    noteText = noteText.trim().toLowerCase();
                    noteText = noteText.substring(0, 1).toUpperCase() + noteText.substring(1);
                    if (U.exist(noteText) && note.getNote() == null || !note.getNote().equals(noteText)){
                        note.setNote(noteText);
                        dao.save(note);
                    }
                }



                notes.forEach(dao::remove);

                planComparator.compare(loadPlan, worker);
                transportationComparator.compare(transportation, worker);
            }

            for (LoadPlan loadPlan : planHashMap.values()) {
                if (!loadPlan.getTransportation().isArchive()) {
                    if (loadPlan.getTransportation().anyAction()) {
                        loadPlan.setCanceled(true);
                        dao.save(loadPlan);
                    } else {
                        dao.remove(loadPlan);
                        dao.remove(loadPlan.getTransportation());
                        DocumentUID uid = dao.getDocumentUID(loadPlan.getUid());
                        if (uid != null) {
                            dao.remove(uid);
                        }
                    }
                }
            }

            boolean saveDeal = false;
            if (quantity > deal.getQuantity()){
                deal.setQuantity(quantity);
                saveDeal = true;

            }
            if (deal.getDate().after(from)){
                deal.setDate(from);
                saveDeal = true;
            }

            if (deal.getDateTo().before(to)){
                deal.setDateTo(to);
                saveDeal = true;
            }

            if (saveDeal){
                dao.save(deal);
            }

            write(resp, answer);
            body.clear();
            planHashMap.clear();
        } else {
            write(resp, emptyBody);
        }

    }
}
