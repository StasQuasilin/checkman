package api.plan;

import api.ServletAPI;
import constants.Branches;
import constants.Constants;
import entity.Worker;
import entity.answers.IAnswer;
import entity.documents.Deal;
import entity.documents.LoadPlan;
import entity.log.comparators.LoadPlanComparator;
import entity.log.comparators.TransportationComparator;
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
            LoadPlan loadPlan = dao.getObjectById(LoadPlan.class, json.get(ID));
            Transportation transportation;
            if (loadPlan == null){
                loadPlan = new LoadPlan();
                loadPlan.setUid(DocumentUIDGenerator.generateUID());
                loadPlan.setDeal(deal);
                loadPlan.setShipper(deal.getShipper());
                transportation = TransportUtil.createTransportation(deal, deal.getCreator(), getWorker(req));
                transportation.setDeal(deal.getId());
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
                transportation.setAmount(plan);
                save = true;
            }

            TransportCustomer customer = TransportCustomer.valueOf(String.valueOf(json.get(Constants.CUSTOMER)));
            if (customer == TransportCustomer.contragent){
                customer = TransportCustomer.cont;
            }
            log.info("\t...Customer: \'" + customer.toString() + "\'");
            if (loadPlan.getCustomer() != customer) {
                loadPlan.setCustomer(customer);
                transportation.setCustomer(customer);
                save = true;
            }

            Vehicle vehicle = dao.getObjectById(Vehicle.class, json.get(VEHICLE));

            if (vehicle != null){
                TransportUtil.setVehicle(transportation, vehicle);
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

            if (save) {
                dao.save(transportation.getCreateTime(), transportation, loadPlan);
                updateUtil.onSave(transportation);
            }

            final HashSet<DocumentNote> notes = new HashSet<>();
            notes.addAll(dao.getTransportationNotesByTransportation(transportation));
            for (Object n : (JSONArray)json.get("notes")){
                JSONObject nj = (JSONObject) n;
                Object noteId = null;
                if (nj.containsKey(Constants.ID)){
                    noteId = nj.get(Constants.ID);
                }
                DocumentNote note = null;
                if (noteId != null) {
                    note = dao.getTransportationNotesById(noteId);
                }
                if (note == null){
                    note = new DocumentNote();
                    note.setTransportation(transportation);
                    note.setDocument(transportation.getUid());
                    note.setTime(new Timestamp(System.currentTimeMillis()));
                    note.setCreator(worker);
                }
                if (notes.contains(note)){
                    notes.remove(note);
                }
                String noteText = (String) nj.get("note");
                noteText = noteText.trim().toLowerCase();
                if (U.exist(noteText) && note.getNote() == null || !note.getNote().equals(noteText)){
                    note.setNote(noteUtil.checkNote(transportation, noteText));
                    dao.save(note);
                    updateUtil.onSave(transportation);
                }
            }
            for (DocumentNote note : notes){
                dao.remove(note);
                updateUtil.onSave(transportation);
            }

            planComparator.compare(loadPlan, worker);
            transportationComparator.compare(transportation, worker);

            IAnswer resultAnswer = new SuccessAnswer();

            if (save){
                resultAnswer.add(ID, loadPlan.getId());
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