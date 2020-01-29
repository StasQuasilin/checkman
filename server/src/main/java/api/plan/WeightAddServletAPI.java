package api.plan;

import api.ServletAPI;
import constants.Branches;
import constants.Constants;
import entity.DealType;
import entity.Worker;
import entity.documents.Deal;
import entity.documents.Shipper;
import entity.documents.LoadPlan;
import entity.organisations.Organisation;
import entity.transport.*;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.DocumentUIDGenerator;
import utils.NoteUtil;
import utils.U;
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
public class WeightAddServletAPI extends ServletAPI {

    private static final String FROM = "from";
    private final Logger log = Logger.getLogger(WeightAddServletAPI.class);
    final UpdateUtil updateUtil = new UpdateUtil();
    private final StorageUtil storageUtil = new StorageUtil();
    private final NoteUtil noteUtil = new NoteUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            log.info(body );
            Date date = Date.valueOf(String.valueOf(body.get("date")));

            float plan = Float.parseFloat(String.valueOf(body.get("plan")));

            Shipper shipper = dao.getShipperList().get(0);
            if(body.containsKey(FROM)){
                String from = String.valueOf(body.get(FROM));
                if (U.exist(from)){
                    shipper = dao.getShipperByValue(from);
                }
            }

            long dealId = Long.parseLong(String.valueOf(body.get("deal")));
            Deal deal;
            Worker creator = getWorker(req);
            Worker manager = dao.getObjectById(body.get(MANAGER));
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

            String number = String.valueOf(body.get(NUMBER));
            if (!U.exist(deal.getNumber()) || !deal.getNumber().equals(number)){
                deal.setNumber(number);
                saveDeal = true;
            }


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

            HashMap<Integer, DocumentNote> alreadyNote = new HashMap<>();
            if (transportation.getNotes() != null) {
                for (DocumentNote note : transportation.getNotes()) {
                    alreadyNote.put(note.getId(), note);
                }
            }
            ArrayList<DocumentNote> liveNotes = new ArrayList<>();

            for (Object o :(JSONArray) body.get("notes")){
                JSONObject note = (JSONObject) o;
                DocumentNote documentNote;
                int noteId = -1;
                if (note.containsKey(ID)){
                    noteId = Integer.parseInt(String.valueOf(note.get(ID)));
                }

                if (alreadyNote.containsKey(noteId)){
                    documentNote = alreadyNote.remove(noteId);
                } else {
                    documentNote = new DocumentNote(transportation, creator);
                    documentNote.setDocument(transportation.getUid());
                }

                String value = String.valueOf(note.get(NOTE));
                if (documentNote.getNote() == null || !documentNote.getNote().equals(value)){
                    documentNote.setNote(noteUtil.checkNote(transportation, value));
                    liveNotes.add(documentNote);
                }
            }

            if (!transportation.isArchive()) {
                transportation.setShipper(shipper);
                JSONObject vehicleJson = (JSONObject) body.get(VEHICLE);
                if (vehicleJson != null){
                    Vehicle vehicle = null;
                    if (vehicleJson.containsKey(Constants.ID)) {
                        long vehicleId = (long) vehicleJson.get(ID);

                        if (vehicleId > 0) {
                            vehicle = dao.getObjectById(Vehicle.class, vehicleId);
                        }

                    }
                    TransportUtil.setVehicle(transportation, vehicle);
                }

                if (body.containsKey(TRAILER)) {
                    JSONObject t = (JSONObject) body.get(TRAILER);
                    Trailer trailer = dao.getObjectById(Trailer.class, t.get(ID));
                    TransportUtil.setTrailer(transportation, trailer);
                }
                if (body.containsKey(TRANSPORTER)){
                    JSONObject t = (JSONObject) body.get(TRANSPORTER);
                    Organisation transporter = dao.getObjectById(Organisation.class, t.get(ID));
                    TransportUtil.setTransporter(transportation, transporter);
                }
                JSONObject driverJson = (JSONObject) body.get(DRIVER);
                if (driverJson != null){
                    Driver driver = null;
                    if (driverJson.containsKey(Constants.ID)){
                        long driverId = (long) driverJson.get(Constants.ID);
                        if (driverId > 0){
                            driver = dao.getDriverByID(driverId);
                        }
                    }
                    TransportUtil.setDriver(transportation, driver);
                }
                transportation.setCreator(creator);
            }

            dao.saveTransportation(transportation);
            transportation.getUsedStorages().forEach(storageUtil::updateStorageEntry);
            dao.saveLoadPlan(loadPlan);

            transportation.getNotes().clear();
            for(DocumentNote note : liveNotes){
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
