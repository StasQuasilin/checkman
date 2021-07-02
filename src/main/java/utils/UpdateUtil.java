package utils;

import api.sockets.ActiveSubscriptions;
import api.sockets.Subscribe;
import api.sockets.handlers.MessageHandler;
import api.sockets.handlers.OnSubscribeHandler;
import entity.DealType;
import entity.Role;
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
import entity.transport.TransportationProduct;
import entity.transport.Vehicle;
import entity.weight.RoundReport;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.hibernate.dao.TransportationDAO;
import utils.hibernate.dao.TransportationStatus;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;

import javax.websocket.Session;
import java.io.IOException;
import java.util.LinkedList;

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

    public void onSave(Deal deal) {
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

    static LinkedList<Subscribe> getSubscriber(Transportation transportation){
        final LinkedList<Subscribe> subscribes = new LinkedList<>();
        for (TransportationProduct product : transportation.getProducts()){

            final Subscribe subscriber = product.getDealProduct().getDeal().getType() == DealType.buy ? Subscribe.TRANSPORT_BUY : Subscribe.TRANSPORT_SELL;
            if (!subscribes.contains(subscriber)){
                subscribes.add(subscriber);
            }
        }
        return subscribes;
    }

    public void onSave(Transportation transportation) {
        for (Subscribe subscribe : getSubscriber(transportation)){
            for(Session session : subscriptions.getSessions(subscribe)){
                final Role sessionView = subscriptions.getSessionView(session);
                final int i = OnSubscribeHandler.calculateSecureMask(sessionView);
                final String s = ActiveSubscriptions.prepareMessage(
                        subscribe, undoAction(
                                Command.update,
                                transportation.toJson(i)
                        )
                );
                subscriptions.send(session, s);
            }
        }
    }

    public void onRemove(Transportation transportation) {
        for (Subscribe subscribe : getSubscriber(transportation)){
            doAction(Command.remove, subscribe, transportation.getId());
        }
    }

    public void onArchive(Transportation transportation) {
        onRemove(transportation);
//        Subscribe subscribe = transportation.getDeal().getType() == DealType.buy ? Subscribe.TRANSPORT_BUY_ARCHIVE : Subscribe.TRANSPORT_SELL_ARCHIVE;
//        doAction(Command.update, subscribe, transportation);
    }

    public void onRemove(ExtractionCrude crude) {
        doAction(Command.remove, Subscribe.EXTRACTION, crude.getId());
    }

    void doAction(Command command, Subscribe sub, Worker worker, Object obj) throws IOException {
        JSONObject json = pool.getObject();
        json.put(command.toString(), obj);
        subscriptions.send(sub, worker, json);
        pool.put(json);
    }
    public JSONObject undoAction(Command command, Object... obj){
        JSONObject json = pool.getObject();
        JSONArray array = pool.getArray();
        for (Object o : obj) {
            array.add(o);
        }
        json.put(command.toString(), array);
        return json;
    }
    public void doAction(Command command, Subscribe subscribe, Object... obj) {
        subscriptions.send(subscribe, undoAction(command, obj));
    }

    public void onSave(Vehicle vehicle) {
        for (Transportation transportation : dao.getTransportationByVehicle(vehicle)){
            if (!transportation.isArchive()) {
                onSave(transportation);
            }
        }
    }

    public void onSave(Driver driver) {
        for (Transportation transportation : dao.getTransportationsByDriver(driver)){
            if (!transportation.isArchive()) {
                onSave(transportation);
            }
        }
    }

    public void onSave(ExtractionTurn turn) {
        doAction(Command.update, Subscribe.EXTRACTION, turn.toJson());
    }

    public void onSave(VROTurn turn) {
        doAction(Command.update, Subscribe.VRO, turn.toJson());
    }

    public void onSave(ProbeTurn turn) {
        doAction(Command.update, Subscribe.PROBES, parser.toJson(turn));
    }

    public void onSave(KPOPart part) {
        doAction(Command.update, Subscribe.KPO, parser.toJson(part));
    }

    public void onSave(StorageTurn turn) {
        doAction(Command.update, Subscribe.LABORATORY_STORAGES, parser.toJson(turn));
    }

    public void onSave(LaboratoryTurn turn) {
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

    public void onSave(Worker worker) {
        JSONObject object = pool.getObject();
        object.put(MessageHandler.CONTACTS, parser.toJson(worker));
        doAction(Command.update, Subscribe.MESSAGES, object);
    }

    public void onArchive(Chat chat) {
        JSONObject object = pool.getObject();
        object.put("id", chat.getId());
        doAction(Command.remove, Subscribe.MESSAGES, object);
    }

    public void onSave(Organisation organisation) {
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

    public void onSave(ManufactureReport manufactureReport) {
        doAction(Command.update, Subscribe.MANUFACTURE_REPORTS, parser.toJson(manufactureReport));
    }

    public void updateStocks(JSONObject json) {
        doAction(Command.update, Subscribe.STOCK, json);
    }

    public void onRemove(ManufactureReport report) {
        doAction(Command.remove, Subscribe.MANUFACTURE_REPORTS, report.getId());
    }

    public void onSave(SealBatch batch) {
        doAction(Command.update, Subscribe.SEALS, batch.toJson());
    }

    public void onSave(BoardItem boardItem) {
        doAction(Command.update, Subscribe.BOARD, boardItem.toJson());
    }

    public void onSave(RoundReport report) {
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
