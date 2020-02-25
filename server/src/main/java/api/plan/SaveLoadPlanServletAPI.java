package api.plan;

import api.ServletAPI;
import constants.Branches;
import constants.Constants;
import entity.Worker;
import entity.answers.IAnswer;
import entity.documents.Deal;
import entity.log.comparators.TransportComparator;
import entity.organisations.Organisation;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@WebServlet(Branches.API.PLAN_LIST_SAVE)
public class SaveLoadPlanServletAPI extends ServletAPI {

    final Logger log = Logger.getLogger(SaveLoadPlanServletAPI.class);
    final TransportComparator transportComparator = new TransportComparator();
    final UpdateUtil updateUtil = new UpdateUtil();
    final NoteUtil noteUtil = new NoteUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);

        if(body != null) {
            log.info(body);
            Deal deal = dao.getObjectById(Deal.class, body.get(DEAL));
            log.info("Save load plan for deal \'" + deal.getId() + "\'...");
            Worker worker = getWorker(req);

            JSONObject json = (JSONObject) body.get(PLAN);

            boolean save = false;
            Transportation transportation = dao.getObjectById(Transportation.class, json.get(ID));;
            if (transportation == null){
                transportation = TransportUtil.createTransportation(deal, deal.getCreator(), getWorker(req));
                transportation.setDeal(deal);
                dao.save(transportation.getCreateTime());
                transportComparator.fix(null);
            } else {
                transportComparator.fix(transportation);
            }

            Date date = Date.valueOf(String.valueOf(json.get(Constants.DATE)));
            log.info("\t...Date: \'" + date.toString() + "\'");
            if (transportation.getDate() == null || !transportation.getDate().equals(date)) {
                transportation.setDate(date);
                save = true;
            }

            float plan = Float.parseFloat(String.valueOf(json.get(Constants.PLAN)));
            log.info("\t...Plan: \'" + plan + "\'");
            if (transportation.getAmount() != plan) {
                transportation.setAmount(plan);
                save = true;
            }

            TransportCustomer customer = TransportCustomer.valueOf(String.valueOf(json.get(Constants.CUSTOMER)));
            if (customer == TransportCustomer.contragent){
                customer = TransportCustomer.cont;
            }
            log.info("\t...Customer: \'" + customer.toString() + "\'");
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
            }else {
                liveNotes.clear();
            }
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
                    documentNote = new DocumentNote(transportation, worker);
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

            if (save) {
                dao.save(transportation);
                updateUtil.onSave(transportation);
            }

            transportComparator.compare(transportation, worker);

            IAnswer resultAnswer = new SuccessAnswer();

            if (save){
                resultAnswer.add(ID, transportation.getId());
            }
            JSONObject ans = resultAnswer.toJson();
            write(resp, ans.toJSONString());
            pool.put(ans);
            body.clear();
        } else {
            write(resp, EMPTY_BODY);
        }
    }
}