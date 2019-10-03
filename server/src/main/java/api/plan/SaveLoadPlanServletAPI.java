package api.plan;

import api.ServletAPI;
import constants.Branches;
import constants.Constants;
import entity.Person;
import entity.Worker;
import entity.answers.IAnswer;
import entity.documents.Deal;
import entity.documents.LoadPlan;
import entity.log.comparators.LoadPlanComparator;
import entity.log.comparators.TransportationComparator;
import entity.transport.*;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.*;
import utils.answers.SuccessAnswer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashSet;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@WebServlet(Branches.API.PLAN_LIST_SAVE)
public class SaveLoadPlanServletAPI extends ServletAPI {

    final Logger log = Logger.getLogger(SaveLoadPlanServletAPI.class);
    final LoadPlanComparator planComparator = new LoadPlanComparator();
    final TransportationComparator transportationComparator = new TransportationComparator();
    final UpdateUtil updateUtil = new UpdateUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);

        if(body != null) {
            log.info(body);
            long dealId = (long) body.get(Constants.DEAL_ID);
            Deal deal = dao.getDealById(dealId);
            log.info("Save load plan for deal \'" + deal.getId() + "\'...");
            Worker worker = getWorker(req);

            JSONObject json = (JSONObject) body.get("plan");
            long id = -1;
            if (json.containsKey(Constants.ID)) {
                id = (long) json.get(Constants.ID);
            }

            boolean save = false;
            LoadPlan loadPlan = null;
            Transportation transportation;
            if (id != -1) {
                loadPlan = dao.getLoadPlanById(id);
            }
            if (loadPlan == null){
                loadPlan = new LoadPlan();
                loadPlan.setUid(DocumentUIDGenerator.generateUID());
                loadPlan.setDeal(deal);
                loadPlan.setShipper(deal.getShipper());
                transportation = new Transportation();
                transportation.setUid(DocumentUIDGenerator.generateUID());
                transportation.setCreator(getWorker(req));
                transportation.setType(deal.getType());
                transportation.setShipper(loadPlan.getShipper());
                transportation.setCounterparty(deal.getOrganisation());
                transportation.setProduct(deal.getProduct());
                transportation.setType(deal.getType());
                transportation.setManager(deal.getCreator());
                loadPlan.setTransportation(transportation);
                planComparator.fix(null);
                transportationComparator.fix(null);
            } else {
                transportation = loadPlan.getTransportation();
                planComparator.fix(loadPlan);
                transportationComparator.fix(transportation);
            }

            Date date = Date.valueOf(String.valueOf(json.get(Constants.DATE)));
            log.info("\t...Date: \'" + date.toString() + "\'");
            if (loadPlan.getDate() == null || !loadPlan.getDate().equals(date)) {
                loadPlan.setDate(date);
                transportation.setDate(date);
                save = true;
            }

            float plan = Float.parseFloat(String.valueOf(json.get(Constants.PLAN)));
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
            JSONObject transportationJSON = (JSONObject) json.get("transportation");

            boolean newVehicle = false;
            Vehicle vehicle = null;
            if (transportationJSON.containsKey("vehicle")) {
                JSONObject vehicleJson = (JSONObject) transportationJSON.get("vehicle");
                long vehicleId = -1;
                if (vehicleJson.containsKey("id")) {
                    vehicleId = (long) vehicleJson.get("id");
                }
                if (vehicleId != -1) {
                    vehicle = dao.getVehicleById(vehicleId);
                    if (vehicle == null) {
                        newVehicle= true;
                        vehicle = new Vehicle();
                        vehicle.setModel(String.valueOf(vehicleJson.get("model")));
                        vehicle.setNumber(String.valueOf(vehicleJson.get("number")));
                        vehicle.setTrailer(String.valueOf(vehicleJson.get("trailer")));
                    }
                }
            }
            boolean newDriver = false;
            Driver driver = null;
            if (transportationJSON.containsKey("driver")) {
                JSONObject driverJson = (JSONObject) transportationJSON.get("driver");
                long driverId = -1;
                if (driverJson.containsKey("id")) {
                    driverId = (long) driverJson.get("id");
                }
                if (driverId != -1) {
                    driver = dao.getDriverByID(driverId);
                    if (driver == null) {
                        newDriver = true;
                        driver = new Driver();
                        Person person = new Person();
                        JSONObject personJson = (JSONObject) driverJson.get("person");
                        person.setSurname(String.valueOf(personJson.get("surname")));
                        person.setForename(String.valueOf(personJson.get("forename")));
                        person.setPatronymic(String.valueOf(personJson.get("patronymic")));
                        driver.setPerson(person);
                    }
                }
            }

            if (newVehicle) {
                dao.save(vehicle);

            }

            if (vehicle != null){
                transportation.setVehicle(vehicle);
                save = true;
            }

            if(newDriver) {
                dao.save(driver.getPerson(), driver);
            }

            if (driver != null) {
                transportation.setDriver(driver);
                save = true;
            }

            if (save) {
                dao.save(transportation, loadPlan);
                updateUtil.onSave(transportation);
            }

            final HashSet<TransportationNote> notes = new HashSet<>();
            notes.addAll(dao.getTransportationNotesByTransportation(transportation));
            for (Object n : (JSONArray)transportationJSON.get("notes")){
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
                    updateUtil.onSave(transportation);
                }
            }
            for (TransportationNote note : notes){
                dao.remove(note);
                updateUtil.onSave(transportation);
            }

            planComparator.compare(loadPlan, worker);
            transportationComparator.compare(transportation, worker);

            IAnswer resultAnswer = new SuccessAnswer();

            if (save){
                resultAnswer.add("id", loadPlan.getId());

            }
            JSONObject ans = parser.toJson(resultAnswer);
            write(resp, ans.toJSONString());
            pool.put(ans);
            body.clear();
        } else {
            write(resp, emptyBody);
        }
    }
}