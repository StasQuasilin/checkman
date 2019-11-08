package api.plan;

import api.ServletAPI;
import constants.Branches;
import constants.Constants;
import entity.DealType;
import entity.Person;
import entity.Worker;
import entity.documents.Deal;
import entity.documents.Shipper;
import entity.documents.LoadPlan;
import entity.transport.*;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.DocumentUIDGenerator;
import utils.TransportUtil;
import utils.UpdateUtil;
import utils.storages.StorageUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by szpt_user045 on 19.04.2019.
 */
@WebServlet(Branches.API.PLAN_LIST_ADD)
public class EditLoadPlanServletAPI extends ServletAPI {

    private final Logger log = Logger.getLogger(EditLoadPlanServletAPI.class);
    final UpdateUtil updateUtil = new UpdateUtil();
    private final StorageUtil storageUtil = new StorageUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            log.info(body );
            Date date = Date.valueOf(String.valueOf(body.get("date")));

            float plan = Float.parseFloat(String.valueOf(body.get("plan")));

            Shipper shipper = dao.getShipperByValue(body.get("from"));
            long dealId = Long.parseLong(String.valueOf(body.get("deal")));
            Deal deal;
            Worker creator = getWorker(req);
            Worker manager = dao.getWorkerById(body.get(MANAGER));
            if (manager == null){
                manager = creator;
            }

            if (dealId == -1){

                deal = new Deal();
                deal.setUid(DocumentUIDGenerator.generateUID());
                deal.setType(DealType.valueOf(String.valueOf(body.get("type"))));
                deal.setDate(date);
                deal.setDateTo(date);
                deal.setOrganisation(dao.getOrganisationById(body.get("organisation")));
                deal.setShipper(shipper);
                deal.setProduct(dao.getProductById(body.get("product")));
                deal.setUnit(dao.getWeightUnitById(body.get("unit")));

                deal.setCreator(creator);
                dao.saveDeal(deal);
                updateUtil.onSave(deal);
            } else {
                deal = dao.getDealById(dealId);
            }

            boolean saveDeal = false;
            float quantity = Float.parseFloat(String.valueOf(body.get("quantity")));
            if (deal.getQuantity() != quantity){
                deal.setQuantity(quantity);
                saveDeal = true;

            }

            float price = Float.parseFloat(String.valueOf(body.get("price")));
            if (deal.getPrice() != price){
                deal.setPrice(price);
                saveDeal = true;
            }

            if (saveDeal){
                dao.saveDeal(deal);
                updateUtil.onSave(deal);
            }

            long id = -1;
            if (body.containsKey(Constants.ID)) {
                id = (long) body.get(Constants.ID);
            }

            LoadPlan loadPlan;
            Transportation transportation;


            if (id != -1) {
                loadPlan = dao.getLoadPlanById(id);
                transportation = loadPlan.getTransportation();

            } else {
                loadPlan = new LoadPlan();
                loadPlan.setDeal(deal);
                transportation = TransportUtil.createTransportation(deal, manager, creator);
                loadPlan.setTransportation(transportation);
            }

            if (loadPlan.getDeal() == null || loadPlan.getDeal().getId() != dealId){
                loadPlan.setDeal(deal);
                transportation.setType(deal.getType());
                transportation.setShipper(deal.getShipper());
                transportation.setCounterparty(deal.getOrganisation());
                transportation.setProduct(deal.getProduct());

                TransportUtil.updateUsedStorages(transportation, getWorker(req));
            }

            loadPlan.setDate(date);
            transportation.setDate(date);
            loadPlan.setShipper(shipper);
            transportation.setShipper(shipper);
            loadPlan.setPlan(plan);
            loadPlan.setCustomer(TransportCustomer.valueOf(String.valueOf(body.get("customer"))));

            HashMap<Integer, TransportationNote> alreadyNote = new HashMap<>();
            if (transportation.getNotes() != null) {
                for (TransportationNote note : transportation.getNotes()) {
                    alreadyNote.put(note.getId(), note);
                }
            }
            ArrayList<TransportationNote> liveNotes = new ArrayList<>();

            for (Object o :(JSONArray) body.get("notes")){
                JSONObject note = (JSONObject) o;
                TransportationNote transportationNote;
                int noteId = -1;
                if (note.containsKey(ID)){
                    noteId = Integer.parseInt(String.valueOf(note.get(ID)));
                }

                if (noteId != -1 && alreadyNote.containsKey(noteId)){
                    transportationNote = alreadyNote.remove(noteId);
                } else {
                    transportationNote = new TransportationNote(transportation, creator);
                }

                String value = String.valueOf(note.get("note"));
                if (transportationNote.getNote() == null || !transportationNote.getNote().equals(value)){
                    transportationNote.setNote(value);
                    liveNotes.add(transportationNote);
                }
            }

            if (!transportation.isArchive()) {
                transportation.setShipper(shipper);
                JSONObject vehicleJson = (JSONObject) body.get("vehicle");
                if (vehicleJson != null){
                    Vehicle vehicle = null;
                    if (vehicleJson.containsKey(Constants.ID)) {
                        long vehicleId = (long) vehicleJson.get("id");
                        if (vehicleId > 0) {
                            vehicle = dao.getVehicleById(vehicleId);
                        } else if (vehicleId == 0){
                            vehicle = new Vehicle();
                            vehicle.setModel(String.valueOf(vehicleJson.get("model")));
                            vehicle.setNumber(String.valueOf(vehicleJson.get("number")));
                            vehicle.setTrailer(String.valueOf(vehicleJson.get("trailer")));
                            dao.save(vehicle);
                        }
                    }
                    if (vehicle != null) {
                        transportation.setVehicle(vehicle);
                    } else if (transportation.getVehicle() != null) {
                        transportation.setVehicle(null);
                    }

                }

                JSONObject driverJson = (JSONObject) body.get("driver");
                if (driverJson != null){
                    Driver driver = null;
                    if (driverJson.containsKey(Constants.ID)){
                        long driverId = (long) driverJson.get(Constants.ID);
                        if (driverId > 0){
                            driver = dao.getDriverByID(driverId);
                        } else if(driverId == 0){
                            driver = new Driver();
                            Person person = new Person();
                            JSONObject personJson = (JSONObject) driverJson.get("person");
                            person.setForename(String.valueOf(personJson.get("forename")));
                            person.setSurname(String.valueOf(personJson.get("surname")));
                            person.setPatronymic(String.valueOf(personJson.get("patronymic")));

                            driver.setPerson(person);
                            dao.save(person, driver);
                        }
                    }
                    if (driver != null) {
                        transportation.setDriver(driver);
                    } else if (transportation.getDriver() != null) {
                        transportation.setDriver(null);
                    }
                }
                transportation.setCreator(creator);
            }

            dao.saveTransportation(transportation);
            transportation.getUsedStorages().forEach(storageUtil::updateStorageEntry);
            dao.saveLoadPlan(loadPlan);

            transportation.getNotes().clear();
            for(TransportationNote note : liveNotes){
                dao.save(note);
                transportation.getNotes().add(note);
            }
            alreadyNote.values().forEach(dao::remove);

            updateUtil.onSave(transportation);
            write(resp, SUCCESS_ANSWER);

        } else {
            write(resp, EMPTY_BODY);
        }
    }
}
