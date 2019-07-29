package utils;

import api.sockets.ActiveSubscriptions;
import api.sockets.Subscriber;
import entity.DealType;
import entity.Worker;
import entity.chat.ChatMessage;
import entity.documents.Deal;
import entity.documents.LoadPlan;
import entity.laboratory.turn.LaboratoryTurn;
import entity.laboratory.probes.ProbeTurn;
import entity.laboratory.storages.StorageTurn;
import entity.laboratory.subdivisions.extraction.ExtractionTurn;
import entity.laboratory.subdivisions.kpo.KPOPart;
import entity.laboratory.subdivisions.vro.VROTurn;
import entity.transport.Driver;
import entity.transport.Transportation;
import entity.transport.Vehicle;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;

import java.io.IOException;

/**
 * Created by szpt_user045 on 11.07.2019.
 */
public class UpdateUtil {
    final JsonPool pool = JsonPool.getPool();
    final ActiveSubscriptions subscriptions = ActiveSubscriptions.getInstance();
    final JsonParser parser = new JsonParser();
    final Logger log = Logger.getLogger(UpdateUtil.class);
    final dbDAO dao = dbDAOService.getDAO();

    public void onSave(Deal deal) throws IOException {
        doAction(Command.update, getSubscriber(deal.getType()), parser.toJson(deal));
    }

    public void onRemove(Deal deal) throws IOException {
        doAction(Command.remove, getSubscriber(deal.getType()), deal.getId());
    }

    Subscriber getSubscriber(DealType type){
        switch (type){
            case buy:
                return Subscriber.DEAL_BUY;
            default:
                return Subscriber.DEAL_SELL;
        }
    }

    public void onSave(Transportation transportation) throws IOException {
        Subscriber subscriber = transportation.getType() == DealType.buy ? Subscriber.TRANSPORT_BUY : Subscriber.TRANSPORT_SELL;
        doAction(Command.update, subscriber, parser.toJson(transportation));
        final LoadPlan plan = dao.getLoadPlanByTransportationId(transportation.getId());
        onSave(plan);
    }

    public void onRemove(Transportation transportation) throws IOException {
        Subscriber subscriber = transportation.getType() == DealType.buy ? Subscriber.TRANSPORT_BUY : Subscriber.TRANSPORT_SELL;
        doAction(Command.remove, subscriber, transportation.getId());
        final LoadPlan plan = dao.getLoadPlanByTransportationId(transportation.getId());
        onRemove(plan);
    }

    public void onSave(LoadPlan plan) throws IOException {
        doAction(Command.update, Subscriber.LOAD_PLAN, parser.toJson(plan));
    }

    public void onRemove(LoadPlan plan) throws IOException {
        doAction(Command.remove, Subscriber.LOAD_PLAN, plan.getId());
    }

    void doAction(Command command, Worker worker, Object ... obj) throws IOException {
        JSONObject json = pool.getObject();
        JSONArray array = pool.getArray();
        for (Object o : obj){
            array.add(o);
        }
        json.put(command.toString(), array);
        subscriptions.send(worker, json.toJSONString());
        pool.put(json);
    }
    void doAction(Command command, Subscriber subscriber, Object ... obj) throws IOException {
        log.info(command.toString().toUpperCase() + " for " + subscriber.toString());
        JSONObject json = pool.getObject();
        JSONArray array = pool.getArray();
        for (Object o : obj){
            array.add(o);
        }
        json.put(command.toString(), array);
        subscriptions.send(subscriber, json.toJSONString());
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
        for (Transportation transportation : dao.getTransportationByDriver(driver)){
            if (!transportation.isArchive()) {
                onSave(transportation);
            }
        }
    }

    public void onSave(ExtractionTurn turn) throws IOException {
        doAction(Command.update, Subscriber.EXTRACTION, parser.toJson(turn));
    }

    public void onSave(VROTurn turn) throws IOException {
        doAction(Command.update, Subscriber.VRO, parser.toJson(turn));
    }

    public void onSave(ProbeTurn turn) throws IOException {
        doAction(Command.update, Subscriber.PROBES, parser.toJson(turn));
    }

    public void onSave(KPOPart part) throws IOException {
        doAction(Command.update, Subscriber.KPO, parser.toJson(part));
    }

    public void onSave(StorageTurn turn) throws IOException {
        doAction(Command.update, Subscriber.LABORATORY_STORAGES, parser.toJson(turn));
    }

    public void onSave(LaboratoryTurn turn) throws IOException{
        doAction(Command.update, Subscriber.LABORATORY_TURNS, parser.toJson(turn));
    }

    public void onSave(ChatMessage message, Worker member) throws IOException {
        doAction(Command.update, member, parser.toJson(message));
    }

    public enum Command {
        update,
        remove
    }
}
