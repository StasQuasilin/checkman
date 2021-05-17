package utils;

import api.sockets.ActiveSubscriptions;
import api.sockets.Subscribe;
import api.sockets.handlers.MessageHandler;
import entity.DealType;
import entity.Worker;
import entity.border.BoardItem;
import entity.chat.Chat;
import entity.chat.ChatMessage;
import entity.documents.Deal;
import entity.laboratory.probes.ProbeTurn;
import entity.laboratory.storages.StorageTurn;
import entity.laboratory.subdivisions.extraction.ExtractionCrude;
import entity.laboratory.subdivisions.extraction.ExtractionTurn;
import entity.laboratory.subdivisions.kpo.KPOPart;
import entity.laboratory.subdivisions.vro.VROTurn;
import entity.laboratory.turn.LaboratoryTurn;
import entity.organisations.Organisation;
import entity.reports.ManufactureReport;
import entity.seals.SealBatch;
import entity.transport.Driver;
import entity.transport.Transportation;
import entity.transport.Vehicle;
import entity.weight.RoundReport;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.hibernate.dao.TransportationDAO;
import utils.hibernate.dao.TransportationStatus;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;

import java.io.IOException;

/**
 * Created by szpt_user045 on 11.07.2019.
 */
public class UpdateUtil {
    private static final String KEY = "key";
    final JsonPool pool = JsonPool.getPool();
    final ActiveSubscriptions subscriptions = ActiveSubscriptions.getInstance();
    final JsonParser parser = new JsonParser();
    final Logger log = Logger.getLogger(UpdateUtil.class);
    final dbDAO dao = dbDAOService.getDAO();
    private final TransportationDAO transportationDAO = new TransportationDAO();

    public void onSave(Deal deal) throws IOException {
        doAction(Command.update, getSubscriber(deal.getType()), deal.toJson());
        for (Transportation t : transportationDAO.getTransportationsByDeal(deal.getId(), TransportationStatus.unarchive)){
            onSave(t);
        }
    }

    public void onRemove(Deal deal) {
        doAction(Command.remove, getSubscriber(deal.getType()), deal.getId());
    }

    public void onArchive(Deal deal) {
        onRemove(deal);
        Subscribe subscribe = deal.getType() == DealType.buy ? Subscribe.DEAL_BUY_ARCHIVE : Subscribe.DEAL_SELL_ARCHIVE;
        doAction(Command.update, subscribe, deal.toJson());
    }

    static Subscribe getSubscriber(DealType type){
        return type == DealType.buy ? Subscribe.DEAL_BUY : Subscribe.DEAL_SELL;
    }

    static Subscribe getSubscriber(Transportation transportation){
        return transportation.getDeal().getType() == DealType.buy ? Subscribe.TRANSPORT_BUY : Subscribe.TRANSPORT_SELL;
    }

    public void onSave(Transportation transportation) throws IOException {
        doAction(Command.update, getSubscriber(transportation), transportation.toJson());
    }

    public void onRemove(Transportation transportation) {
        doAction(Command.remove, getSubscriber(transportation), transportation.getId());
    }

    public void onArchive(Transportation transportation) {
        onRemove(transportation);
        Subscribe subscribe = transportation.getDeal().getType() == DealType.buy ? Subscribe.TRANSPORT_BUY_ARCHIVE : Subscribe.TRANSPORT_SELL_ARCHIVE;
        doAction(Command.update, subscribe, transportation);
    }

    public void onRemove(ExtractionCrude crude) throws IOException {
        doAction(Command.remove, Subscribe.EXTRACTION, crude.getId());
    }

    void doAction(Command command, Subscribe sub, Worker worker, Object obj) throws IOException {
        JSONObject json = pool.getObject();
        json.put(command.toString(), obj);
        subscriptions.send(sub, worker, json);
        pool.put(json);
    }
    public void doAction(Command command, Subscribe subscribe, Object... obj) {
        JSONObject json = pool.getObject();
        JSONArray array = pool.getArray();
        for (Object o : obj) {
            array.add(o);
        }
        json.put(command.toString(), array);
        subscriptions.send(subscribe, json);

        pool.put(json);
    }

    public void onSave(Vehicle vehicle) throws IOException {
        for (Transportation transportation : dao.getTransportationByVehicle(vehicle)){
            if (!transportation.isArchive()) {
                onSave(transportation);
            }
        }
    }

    public void onSave(Driver driver) throws IOException {
        for (Transportation transportation : dao.getTransportationsByDriver(driver)){
            if (!transportation.isArchive()) {
                onSave(transportation);
            }
        }
    }

    public void onSave(ExtractionTurn turn) throws IOException {
        doAction(Command.update, Subscribe.EXTRACTION, turn.toJson());
    }

    public void onSave(VROTurn turn) throws IOException {
        doAction(Command.update, Subscribe.VRO, turn.toJson());
    }

    public void onSave(ProbeTurn turn) {
        doAction(Command.update, Subscribe.PROBES, parser.toJson(turn));
    }

    public void onSave(KPOPart part) throws IOException {
        doAction(Command.update, Subscribe.KPO, parser.toJson(part));
    }

    public void onSave(StorageTurn turn) throws IOException {
        doAction(Command.update, Subscribe.LABORATORY_STORAGES, parser.toJson(turn));
    }

    public void onSave(LaboratoryTurn turn) throws IOException{
        doAction(Command.update, Subscribe.LABORATORY_TURNS, parser.toJson(turn));
    }

    public void onSave(ChatMessage message, Worker member) throws IOException {
        JSONArray array = pool.getArray();
        array.add(parser.toJson(message));

        JSONObject object = pool.getObject();
        object.put(MessageHandler.MESSAGES, array);

        doAction(Command.update, Subscribe.MESSAGES, member, object);
    }

    public void onSave(Chat chat, String chatKey, Worker member) throws IOException {

        JSONObject json = parser.toJson(chat);
        json.put(KEY, chatKey);

        JSONArray array = pool.getArray();
        array.add(json);

        JSONObject object = pool.getObject();
        object.put(MessageHandler.CHATS, array);

        doAction(Command.update, Subscribe.MESSAGES, member, object);
    }

    public void onSave(Worker worker) throws IOException {
        JSONObject object = pool.getObject();
        object.put(MessageHandler.CONTACTS, parser.toJson(worker));
        doAction(Command.update, Subscribe.MESSAGES, object);
    }

    public void onArchive(Chat chat) throws IOException {
        JSONObject object = pool.getObject();
        object.put("id", chat.getId());
        doAction(Command.remove, Subscribe.MESSAGES, object);
    }

    public void onSave(Organisation organisation) throws IOException {
        for (Deal deal : dao.getDealsByOrganisation(organisation)){
            onSave(deal);
        }
        for (Transportation transportation : dao.getTransportationByOrganisation(organisation)){
            onSave(transportation);
        }
        for (Transportation t : dao.getTransportationsByTransporter(organisation)){
            onSave(t);
        }
    }

    public void onSave(ManufactureReport manufactureReport) throws IOException {
        doAction(Command.update, Subscribe.MANUFACTURE_REPORTS, parser.toJson(manufactureReport));
    }

    public void updateStocks(JSONObject json) throws IOException {
        doAction(Command.update, Subscribe.STOCK, json);
    }

    public void onRemove(ManufactureReport report) throws IOException {
        doAction(Command.remove, Subscribe.MANUFACTURE_REPORTS, report.getId());
    }

    public void onSave(SealBatch batch) {
        doAction(Command.update, Subscribe.SEALS, batch.toJson());
    }

    public void onSave(BoardItem boardItem) throws IOException {
        doAction(Command.update, Subscribe.BOARD, boardItem.toJson());
    }

    public void onSave(RoundReport report) throws IOException {
        doAction(Command.update, Subscribe.ROUND_REPORT, report.toJson());
    }

    public void onRemove(SealBatch batch) {
        doAction(Command.remove, Subscribe.SEALS, batch.toJson());
    }

    public enum Command {
        add,
        update,
        remove
    }
}
