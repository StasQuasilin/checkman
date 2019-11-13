package api.plan;

import api.ServletAPI;
import constants.Branches;
import constants.Constants;
import entity.DealType;
import entity.Person;
import entity.Worker;
import entity.deal.Contract;
import entity.deal.ContractProduct;
import entity.documents.Deal;
import entity.documents.Shipper;
import entity.documents.LoadPlan;
import entity.organisations.Organisation;
import entity.products.Product;
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
            long contractId = Long.parseLong(String.valueOf(body.get("deal")));

            ContractProduct contractProduct;
            Worker creator = getWorker(req);
            Worker manager = dao.getWorkerById(body.get(MANAGER));
            if (manager == null){
                manager = creator;
            }

            if (contractId == -1){
                Contract contract = new Contract();
                contract.setFrom(date);
                contract.setTo(date);
                contract.setUid(DocumentUIDGenerator.generateUID());
                contract.setCounterparty(dao.getObjectById(Organisation.class, body.get("organisation")));
                contract.setCreator(creator);
                contract.setManager(manager);
                dao.save(contract);

                contractProduct = new ContractProduct();
                contractProduct.setContract(contract);
                contractProduct.setType(DealType.valueOf(String.valueOf(body.get("type"))));
                contractProduct.setShipper(shipper);
                contractProduct.setProduct(dao.getObjectById(Product.class, body.get("product")));
                contractProduct.setUnit(dao.getWeightUnitById(body.get("unit")));
                contractProduct.setAmount(plan);
                dao.save(contractProduct);

                updateUtil.onSave(contract);
            } else {
                contractProduct = dao.getObjectById(ContractProduct.class, contractId);
            }

            boolean saveDeal = false;
            float quantity = Float.parseFloat(String.valueOf(body.get("quantity")));
            if (contractProduct.getAmount() != quantity){
                contractProduct.setAmount(quantity);
                saveDeal = true;
            }

            float price = Float.parseFloat(String.valueOf(body.get("price")));
            if (contractProduct.getPrice() != price){
                contractProduct.setPrice(price);
                saveDeal = true;
            }

            if (saveDeal){
                dao.save(contractProduct);
                updateUtil.onSave(contractProduct.getContract());
            }

            long id = -1;
            if (body.containsKey(Constants.ID)) {
                id = (long) body.get(Constants.ID);
            }

            Transportation2 transportation;
            TransportationProduct transportationProduct = null;

            if (id != -1) {
                transportationProduct = dao.getObjectById(TransportationProduct.class, id);
            }
            if (transportationProduct == null) {
                transportation = new Transportation2();
                transportationProduct = new TransportationProduct();
                transportationProduct.setPlan(plan);
                transportationProduct.setTransportation(transportation);
            } else {
                transportation = transportationProduct.getTransportation();
            }

            if (transportationProduct.getContractProduct() == null || transportationProduct.getContractProduct().getId() != contractId){
                transportationProduct.setContractProduct(contractProduct);
                TransportUtil.updateUsedStorages(transportationProduct, getWorker(req));
            }

            transportation.setDate(date);
            transportation.setShipper(shipper);

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
                JSONObject truckJson = (JSONObject) body.get("truck");
                if (truckJson != null){
                    Truck truck;
                    if (truckJson.containsKey(ID)) {
                        truck = dao.getObjectById(Truck.class, truckJson.get(ID));
                        if (truck != null){
                            TransportUtil.setTruck(transportation, truck);
                        }else {
                            TransportUtil.setTruck(transportation, null);
                        }
                    }
                }

                JSONObject driverJson = (JSONObject) body.get("driver");
                if (driverJson != null){
                    Driver driver;
                    if (driverJson.containsKey(ID)){
                        driver = dao.getObjectById(Driver.class, driverJson.get(ID));
                        if (driver != null){
                            TransportUtil.setDriver(transportation, driver);
                        }
                    }
                }
            }

            dao.save(transportationProduct);

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
