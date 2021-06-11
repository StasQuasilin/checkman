package utils.transport;

import constants.Constants;
import entity.Worker;
import entity.documents.DealProduct;
import entity.log.comparators.TransportComparator;
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
import utils.hibernate.dao.DealDAO;
import utils.hibernate.dao.TransportationDAO;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;
import utils.json.JsonObject;

import java.io.IOException;
import java.sql.Date;
import java.util.*;

import static constants.Constants.*;

public class TransportationEditor {
    private final Logger log = Logger.getLogger(TransportationEditor.class);
    private final dbDAO dao = dbDAOService.getDAO();
    private final TransportComparator transportComparator = new TransportComparator();
    private final NoteUtil noteUtil = new NoteUtil();
    private final UpdateUtil updateUtil = new UpdateUtil();
    private final TransportationDAO transportationDAO = new TransportationDAO();
    private final DealDAO dealDAO = new DealDAO();

    public Transportation saveTransportation(JSONObject json, Worker creator, Worker manager) throws IOException {

        boolean save = false;
        boolean isNew = false;

        Transportation transportation = dao.getObjectById(Transportation.class, json.get(ID));
        if (transportation == null){
            isNew = true;
            transportation = TransportUtil.createTransportation(manager, creator);
            transportComparator.fix(null);
        } else {
            transportComparator.fix(transportation);
        }

        if (json.containsKey(ADDRESS)){
            Address address = dao.getObjectById(Address.class, json.get(ADDRESS));
            if (transportation.getAddress() == null || transportation.getAddress().getId() != address.getId()){
                transportation.setAddress(address);
                save = true;
            }
        } else if (transportation.getAddress() != null){
            transportation.setAddress(null);
            save = true;
        }

        Date date = Date.valueOf(String.valueOf(json.get(Constants.DATE)));
        if (transportation.getDate() == null || !transportation.getDate().equals(date)) {
            transportation.setDate(date);
            save = true;
        }

        TransportCustomer customer;
        if (json.containsKey(CUSTOMER)){
            customer = TransportCustomer.valueOf(String.valueOf(json.get(CUSTOMER)));
        } else {
            customer = TransportCustomer.szpt;
        }

        if (customer == TransportCustomer.contragent){
            customer = TransportCustomer.cont;
        }

        if (transportation.getCustomer() != customer) {
            transportation.setCustomer(customer);
            save = true;
        }

        Trailer trailer = dao.getObjectById(Trailer.class, json.get(TRAILER));
        final Trailer t = transportation.getTrailer();

        if (changeIt(trailer, t)){
            TransportUtil.setTrailer(transportation, trailer);
            save = true;
        }

        Vehicle vehicle = dao.getObjectById(Vehicle.class, json.get(VEHICLE));
        final Vehicle v = transportation.getVehicle();

        if (changeIt(vehicle, v)){
            TransportUtil.setVehicle(transportation, vehicle);
            save = true;
        }

        Organisation transporter = dao.getObjectById(Organisation.class, json.get(TRANSPORTER));
        if (transporter != null){
            TransportUtil.setTransporter(transportation, transporter);
            save = true;
        }

        Driver driver = dao.getObjectById(Driver.class, json.get(DRIVER));
        final Driver d = transportation.getDriver();
        if (changeIt(driver, d)) {
            TransportUtil.setDriver(transportation, driver, transporter == null);
            save = true;
        }

        HashMap<Integer, DocumentNote> alreadyNote = new HashMap<>();
        if (transportation.getNotes() != null) {
            for (DocumentNote note : transportation.getNotes()) {
                alreadyNote.put(note.getId(), note);
            }
        }

        List<DocumentNote> liveNotes = transportation.getNotes();
        if (liveNotes == null){
            liveNotes = new ArrayList<>();
        } else {
            liveNotes.clear();
        }
        if (json.containsKey(NOTES)){
            boolean saveNote;
            for (Object o :(JSONArray) json.get(NOTES)){
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
                saveNote = false;
                String s = noteUtil.checkNote(transportation, value);
                if (U.exist(s)){
                    documentNote.setNote(s);
                    saveNote = true;
                    save = true;
                } else {
                    if (documentNote.getId() > 0){
                        dao.remove(documentNote);
                        save = true;
                    }
                }
                if (saveNote) {
                    liveNotes.add(documentNote);
                }
            }

            for (DocumentNote note : alreadyNote.values()){
                dao.remove(note);
            }
        }

        if (transportation.getManager() != manager){
            transportation.setManager(manager);
            save = true;
        }

        if (save) {
            transportationDAO.saveTransportation(transportation, isNew);
        }
        final boolean update = saveTransportationProducts((JSONArray) json.get(PRODUCTS), transportation);
        if (save || update){
            updateUtil.onSave(transportation);
        }

        transportComparator.compare(transportation, creator);

        return transportation;
    }

    private boolean changeIt(Object o1, Object o2) {
        if (o1 == null){
            return o2 != null;
        } else {
            return !o1.equals(o2);
        }
    }

    private HashMap<Integer, TransportationProduct> buildProductMap(Transportation transportation) {
        final HashMap<Integer, TransportationProduct> hashMap = new HashMap<>();
        final Set<TransportationProduct> products = transportation.getProducts();
        if(products != null) {
            for (TransportationProduct product : products) {
                hashMap.put(product.getId(), product);
            }
        }
        return hashMap;
    }

    public boolean saveTransportationProducts(JSONArray array, Transportation transportation) {

        final HashMap<Integer, TransportationProduct> hashMap = buildProductMap(transportation);
        final Set<TransportationProduct> products = transportation.getProducts();
        final int productsCount = products.size();
        products.clear();

        int index = 0;
        boolean update = false;
        for (Object o : array){
            JsonObject object = new JsonObject((JSONObject) o);
            final int id = object.getInt(ID);
            TransportationProduct product = hashMap.remove(id);
            boolean saveIt = false;
            if(product == null){
                product = new TransportationProduct();
                product.setTransportation(transportation);
                product.setUid(DocumentUIDGenerator.generateUID());
            }

            final DealProduct dealProduct = dealDAO.getDealProduct(object.getInt(DEAL_PRODUCT));
            if(dealProduct == null){
                log.warn("--- No deal product for " + index + " product ");
                continue;
            }

            final DealProduct dp = product.getDealProduct();
            if (changeIt(dp, dealProduct)){
                product.setDealProduct(dealProduct);
                saveIt = true;
            }
            final int amount = object.getInt(AMOUNT);
            if (product.getAmount() != amount){
                product.setAmount(amount);
                saveIt = true;
            }
            if (product.getIndex() != index){
                product.setIndex(index);
                saveIt = true;
            }

            if(saveIt){
                update = true;
                transportationDAO.saveProduct(product);
            }
            index++;
            products.add(product);
        }
        for (TransportationProduct product : hashMap.values()){
            transportationDAO.removeProduct(product);
        }

        if (!update){
            update = productsCount != products.size();
        }

        return update;
    }
}
