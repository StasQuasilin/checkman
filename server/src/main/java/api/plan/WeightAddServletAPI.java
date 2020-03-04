package api.plan;

import api.ServletAPI;
import api.deal.DealEditor;
import constants.Branches;
import constants.Constants;
import entity.DealType;
import entity.Worker;
import entity.documents.Deal;
import entity.documents.Shipper;
import entity.log.comparators.DealComparator;
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

    private DealEditor dealEditor = new DealEditor();
    private static final String FROM = "from";
    private final Logger log = Logger.getLogger(WeightAddServletAPI.class);
    final UpdateUtil updateUtil = new UpdateUtil();
    private final StorageUtil storageUtil = new StorageUtil();
    private final NoteUtil noteUtil = new NoteUtil();
    private final DealComparator dealComparator = new DealComparator();
    private final TransportComparator transportComparator = new TransportComparator();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            log.info(body );
            Date date = Date.valueOf(String.valueOf(body.get(DATE)));

            float plan = Float.parseFloat(String.valueOf(body.get(PLAN)));

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

            Deal deal = dealEditor.editDeal((JSONObject) body.get(DEAL), creator);

            Transportation transportation = dao.getObjectById(Transportation.class, body.get(ID));
            if (transportation == null) {
                transportComparator.fix(null);
                transportation = TransportUtil.createTransportation(deal, manager, creator);
                dao.save(transportation.getCreateTime());
            } else {
                transportComparator.fix(transportation);
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

            List<DocumentNote> liveNotes = transportation.getNotes();
            if (liveNotes == null){
                liveNotes = new ArrayList<>();
            }else {
                liveNotes.clear();
            }
            boolean saveNote;
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
                saveNote = false;
                String s = noteUtil.checkNote(transportation, value);
                if (U.exist(s)){
                    documentNote.setNote(s);
                    saveNote = true;
                } else {
                    if (documentNote.getId() > 0){
                        dao.remove(documentNote);
                    }
                }
                if (saveNote) {
                    liveNotes.add(documentNote);
                }
            }

            if (!transportation.isArchive()) {
                transportation.setShipper(shipper);
                TransportUtil.setVehicle(transportation, dao.getObjectById(Vehicle.class, body.get(VEHICLE)));
                TransportUtil.setTrailer(transportation, dao.getObjectById(Trailer.class, body.get(TRAILER)));
                TransportUtil.setTransporter(transportation, dao.getObjectById(Organisation.class, body.get(TRANSPORTER)));
                TransportUtil.setDriver(transportation, dao.getObjectById(Driver.class, body.get(DRIVER)));
            }

            dao.save(transportation);
            dao.getUsedStoragesByTransportation(transportation).forEach(storageUtil::updateStorageEntry);

            alreadyNote.values().forEach(dao::remove);

            updateUtil.onSave(transportation);
            transportComparator.compare(transportation, creator);

            write(resp, SUCCESS_ANSWER);
            List<TransportStorageUsed> u = dao.getUsedStoragesByTransportation(transportation);
            TransportUtil.updateUsedStorages(transportation, u, getWorker(req));
        } else {
            write(resp, EMPTY_BODY);
        }
    }
}
