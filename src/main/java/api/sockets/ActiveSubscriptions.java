package api.sockets;

import api.sockets.handlers.*;
import entity.DealType;
import entity.Worker;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import utils.JsonPool;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;

import javax.websocket.Session;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by szpt_user045 on 11.07.2019.
 */
public class ActiveSubscriptions {

    private final dbDAO dao = dbDAOService.getDAO();
    private static ActiveSubscriptions instance = new ActiveSubscriptions();
    private final Logger log = Logger.getLogger(ActiveSubscriptions.class);
    final HashMap<Subscriber, OnSubscribeHandler> handlers = new HashMap<>();
    final HashMap<Subscriber, ArrayList<Session>> bySubscribe = new HashMap<>();
    final HashMap<Integer, Session> byWorker = new HashMap<>();
    final MessageHandler messageHandler = new MessageHandler();
    public static final JsonPool pool = JsonPool.getPool();
    public static final String TYPE = "type";
    private static final String DATA = "data";

    private ActiveSubscriptions(){
        for(Subscriber s : Subscriber.values()){
            bySubscribe.put(s, new ArrayList<>());
        }
        handlers.put(Subscriber.DEAL_BUY, new DealHandler(DealType.buy, Subscriber.DEAL_BUY));
        handlers.put(Subscriber.DEAL_BUY_ARCHIVE, new DealArchiveHandler(DealType.buy, Subscriber.DEAL_BUY_ARCHIVE));
        handlers.put(Subscriber.DEAL_SELL, new DealHandler(DealType.sell, Subscriber.DEAL_SELL));
        handlers.put(Subscriber.DEAL_SELL_ARCHIVE, new DealArchiveHandler(DealType.sell, Subscriber.DEAL_SELL_ARCHIVE));
        handlers.put(Subscriber.LOAD_PLAN, new LoadPlanHandler(Subscriber.LOAD_PLAN));
        handlers.put(Subscriber.LOAD_PLAN_ARCHIVE, new LoadPlanArchiveHandler(Subscriber.LOAD_PLAN_ARCHIVE));
        handlers.put(Subscriber.LOGISTIC, new LogisticHandler(Subscriber.LOGISTIC));
        handlers.put(Subscriber.TRANSPORT_BUY, new TransportHandler(Subscriber.TRANSPORT_BUY, DealType.buy));
        handlers.put(Subscriber.TRANSPORT_SELL, new TransportHandler(Subscriber.TRANSPORT_SELL, DealType.sell));
        handlers.put(Subscriber.PROBES, new ProbesHandler(Subscriber.PROBES));
        handlers.put(Subscriber.EXTRACTION, new ExtractionHandler(Subscriber.EXTRACTION));
        handlers.put(Subscriber.VRO, new VroHandler(Subscriber.VRO));
        handlers.put(Subscriber.KPO, new KpoHandler(Subscriber.KPO));
        handlers.put(Subscriber.LABORATORY_STORAGES, new StoragesHandler(Subscriber.LABORATORY_STORAGES));
        handlers.put(Subscriber.LABORATORY_TURNS, new TurnHandler(Subscriber.LABORATORY_TURNS));
    }

    public static ActiveSubscriptions getInstance() {
        return instance;
    }

    public void subscribe(Subscriber sub, Session session, long workerId) throws IOException {
        if (sub != Subscriber.MESSAGES) {
            bySubscribe.get(sub).add(session);
            if (handlers.containsKey(sub)) {
                handlers.get(sub).handle(session);
            }
        } else {
            Worker worker = dao.getWorkerById(workerId);
            byWorker.put(worker.getId(), session);
            messageHandler.handle(worker, session);
        }


        log.info("Session #" + session.getId() + ", subscribe on " + sub.toString());
    }
    public void unSubscribe(Subscriber sub, Session session){
        bySubscribe.get(sub).remove(session);
    }

    public synchronized void send(Subscriber sub, String txt) throws IOException {
        txt = prepareMessage(sub, txt);
        for (Session session : bySubscribe.get(sub)){
            if (session.isOpen()){
                session.getBasicRemote().sendText(txt);
            }
        }
    }
    public synchronized void send(Worker worker, String message) throws IOException {
        if (byWorker.containsKey(worker.getId())) {
            Session session = byWorker.get(worker.getId());
            if(session.isOpen()) {
                session.getBasicRemote().sendText(prepareMessage(Subscriber.MESSAGES.toString(), message));
            } else {
                byWorker.remove(worker.getId());
            }

        } else {
            System.out.println("No worker " + worker.getId());
        }
    }
    public static String prepareMessage(Subscriber type, String msg){
        return prepareMessage(type.toString(), msg);
    }

    public static String prepareMessage(String type, String msg){
        JSONObject object = pool.getObject();
        object.put(TYPE, type);
        object.put(DATA, msg);
        return object.toJSONString();
    }
}
