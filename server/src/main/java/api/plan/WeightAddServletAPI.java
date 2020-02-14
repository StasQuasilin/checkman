package api.plan;

import api.ServletAPI;
import constants.Branches;
import constants.Constants;
import entity.DealType;
import entity.Worker;
import entity.documents.Deal;
import entity.documents.Shipper;
import entity.organisations.Address;
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
import java.util.List;

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

            Worker creator = getWorker(req);
            Worker manager = dao.getObjectById(body.get(MANAGER));
            if (manager == null){
                manager = creator;
            }

            Deal deal = dao.getObjectById(Deal.class, body.get(DEAL));
            if (deal == null){
                deal = new Deal();
                deal.setUid(DocumentUIDGenerator.generateUID());
                deal.setType(DealType.valueOf(String.valueOf(body.get(TYPE))));

                deal.setDate(date);
                deal.setDateTo(date);
                deal.setOrganisation(dao.getOrganisationById(body.get(ORGANISATION)));
                deal.setShipper(shipper);
                deal.setProduct(dao.getProductById(body.get(PRODUCT)));
                deal.setUnit(dao.getWeightUnitById(body.get(UNIT)));

                deal.setCreator(creator);
                dao.saveDeal(deal);
                updateUtil.onSave(deal);
            }

            boolean saveDeal = false;

            String number = String.valueOf(body.get(NUMBER));
            if (!U.exist(deal.getNumber()) || !deal.getNumber().equals(number)){
                deal.setNumber(number);
                saveDeal = true;
            }

            float quantity = Float.parseFloat(String.valueOf(body.get(QUANTITY)));
            if (deal.getQuantity() != quantity){
                deal.setQuantity(quantity);
                saveDeal = true;
            }

            float price = Float.parseFloat(String.valueOf(body.get(PRICE)));
            if (deal.getPrice() != price){
                deal.setPrice(price);
                saveDeal = true;
            }

            if (saveDeal){
                dao.saveDeal(deal);
                updateUtil.onSave(deal);
            }

            Transportation transportation = dao.getObjectById(Transportation.class, body.get(ID));
            if (transportation == null) {
                transportation = TransportUtil.createTransportation(deal, manager, creator);
                dao.save(transportation.getCreateTime());
            }

            if (transportation.getDeal().getId() != deal.getId()){
                transportation.setDeal(deal);
                transportation.setShipper(deal.getShipper());
                transportation.setProduct(deal.getProduct());
            }

            transportation.setDate(date);
            transportation.setShipper(shipper);
            Address address = dao.getObjectById(Address.class, body.get(ADDRESS));
            transportation.setAddress(address);
            transportation.setAmount(plan);
            TransportCustomer customer = TransportCustomer.valueOf(String.valueOf(body.get(CUSTOMER)));
            if (customer == TransportCustomer.contragent){
                customer = TransportCustomer.cont;
            }
            transportation.setCustomer(customer);

            HashMap<Integer, DocumentNote> alreadyNote = new HashMap<>();
            if (transportation.getNotes() != null) {
                for (DocumentNote note : transportation.getNotes()) {
                    alreadyNote.put(note.getId(), note);
                }
            }
            ArrayList<DocumentNote> liveNotes = new ArrayList<>();

            for (Object o :(JSONArray) body.get(NOTES)){
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
            }


            dao.save(transportation);
            dao.getUsedStoragesByTransportation(transportation).forEach(storageUtil::updateStorageEntry);

            transportation.getNotes().clear();
            for(DocumentNote note : liveNotes){
                dao.save(note);
                transportation.getNotes().add(note);
            }
            alreadyNote.values().forEach(dao::remove);

            updateUtil.onSave(transportation);
            write(resp, SUCCESS_ANSWER);
            List<TransportStorageUsed> u = dao.getUsedStoragesByTransportation(transportation);
            TransportUtil.updateUsedStorages(transportation, u, getWorker(req));
        } else {
            write(resp, EMPTY_BODY);
        }
    }
}
