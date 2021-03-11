package utils.transport;

import constants.Constants;
import entity.Worker;
import entity.documents.Deal;
import entity.documents.DealProduct;
import entity.log.comparators.TransportComparator;
import entity.organisations.Address;
import entity.organisations.Organisation;
import entity.transport.*;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.NoteUtil;
import utils.U;
import utils.UpdateUtil;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;

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

    public Transportation saveTransportation(Deal deal, DealProduct product, JSONObject json, Worker creator, Worker manager) throws IOException {

        boolean save = false;
        boolean isNew = false;

        Transportation transportation = dao.getObjectById(Transportation.class, json.get(ID));
        if (transportation == null){
            isNew = true;
            transportation = TransportUtil.createTransportation(deal, manager, creator);
            transportComparator.fix(null);
        } else {
            transportComparator.fix(transportation);
        }

//        final List<TransportationProduct> transportationProducts = buildProductMap(transportation);

        if (transportation.getDeal() == null || transportation.getDeal().getId() != deal.getId()){
            transportation.setDeal(deal);
            save = true;
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
        log.info("\t...Date: '" + date.toString() + "'");
        if (transportation.getDate() == null || !transportation.getDate().equals(date)) {
            transportation.setDate(date);
            save = true;
        }
        boolean updateDeal = false;
        if (deal.getDateTo().before(date)){
            deal.setDateTo(date);
            updateDeal = true;
        }

        if (deal.getDate().after(date)){
            deal.setDate(date);
            updateDeal = true;
        }

        if (updateDeal){
            dao.save(deal);
            updateUtil.onSave(deal);
        }

        float plan = U.parseFloat(String.valueOf(json.get(PLAN)));
        log.info("\t...Plan: '" + plan + "'");
        if (transportation.getAmount() != plan) {
            transportation.setAmount(plan);
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

        log.info("\t...Customer: '" + customer.toString() + "'");
        if (transportation.getCustomer() != customer) {
            transportation.setCustomer(customer);
            save = true;
        }

        Vehicle vehicle = dao.getObjectById(Vehicle.class, json.get(VEHICLE));
        if (vehicle != null){
            TransportUtil.setVehicle(transportation, vehicle);
            save = true;
        }

        Trailer trailer = dao.getObjectById(Trailer.class, json.get(TRAILER));
        if (trailer != null){
            TransportUtil.setTrailer(transportation, trailer);
            save = true;
        }

        Driver driver = dao.getObjectById(Driver.class, json.get(DRIVER));
        if (driver != null) {
            TransportUtil.setDriver(transportation, driver);
            save = true;
        }

        Organisation transporter = dao.getObjectById(Organisation.class, json.get(TRANSPORTER));
        if (transporter != null){
            TransportUtil.setTransporter(transportation, transporter);
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
            if(isNew){
                dao.save(transportation.getCreateTime());
            }
            dao.save(transportation);
            updateUtil.onSave(transportation);
        }

        transportComparator.compare(transportation, creator);

        return transportation;
    }

    private List<TransportationProduct> buildProductMap(Transportation transportation) {
        return new LinkedList<>(transportation.getProducts());
    }
}
