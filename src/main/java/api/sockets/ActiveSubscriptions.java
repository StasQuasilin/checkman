package api.sockets;

import api.sockets.handlers.*;
import entity.DealType;
import entity.Role;
import entity.Worker;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import utils.JsonPool;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;
import utils.json.JsonObject;

import javax.websocket.Session;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by szpt_user045 on 11.07.2019.
 */
public class ActiveSubscriptions {


    private static final ActiveSubscriptions instance = new ActiveSubscriptions();
    private final Logger log = Logger.getLogger(ActiveSubscriptions.class);
    final HashMap<Subscribe, OnSubscribeHandler> handlers = new HashMap<>();

    final HashMap<Subscribe, LinkedList<Session>> bySubscribe = new HashMap<>();
    final HashMap<Subscribe, LinkedList<Worker>> workers = new HashMap<>();
    final HashMap<Session, Role> views = new HashMap<>();
    final HashMap<Worker, LinkedList<Session>> byWorker = new HashMap<>();
    final MessageHandler messageHandler = new MessageHandler();
    final SessionTimer sessionTimer = SessionTimer.getInstance();
    public static final JsonPool pool = JsonPool.getPool();
    public static final String TYPE = "type";
    private static final String DATA = "data";

    private ActiveSubscriptions(){
        handlers.put(Subscribe.CONTRACTS_BUY, new ContractHandler(DealType.buy, Subscribe.CONTRACTS_BUY));
        handlers.put(Subscribe.CONTRACTS_SELL, new ContractHandler(DealType.sell, Subscribe.CONTRACTS_SELL));
        handlers.put(Subscribe.DEAL_BUY, new DealHandler(DealType.buy, Subscribe.DEAL_BUY));
        handlers.put(Subscribe.DEAL_BUY_ARCHIVE, new DealArchiveHandler(DealType.buy, Subscribe.DEAL_BUY_ARCHIVE));
        handlers.put(Subscribe.DEAL_SELL, new DealHandler(DealType.sell, Subscribe.DEAL_SELL));
        handlers.put(Subscribe.DEAL_SELL_ARCHIVE, new DealArchiveHandler(DealType.sell, Subscribe.DEAL_SELL_ARCHIVE));
        handlers.put(Subscribe.LOGISTIC, new LogisticHandler(Subscribe.LOGISTIC));
        handlers.put(Subscribe.DEAL_TRANSPORT_BUY, new TransportHandler(Subscribe.TRANSPORT_BUY, DealType.buy, true));
        handlers.put(Subscribe.DEAL_TRANSPORT_SELL, new TransportHandler(Subscribe.TRANSPORT_BUY, DealType.buy, true));
        handlers.put(Subscribe.TRANSPORT_BUY, new TransportHandler(Subscribe.TRANSPORT_BUY, DealType.buy));
        handlers.put(Subscribe.TRANSPORT_SELL, new TransportHandler(Subscribe.TRANSPORT_SELL, DealType.sell));
        handlers.put(Subscribe.TRANSPORT_BUY_ARCHIVE, new TransportArchiveHandler(DealType.buy, Subscribe.TRANSPORT_BUY_ARCHIVE));
        handlers.put(Subscribe.TRANSPORT_SELL_ARCHIVE, new TransportArchiveHandler(DealType.sell, Subscribe.TRANSPORT_SELL_ARCHIVE));
        handlers.put(Subscribe.PROBES, new ProbesHandler(Subscribe.PROBES));
        handlers.put(Subscribe.EXTRACTION, new ExtractionHandler(Subscribe.EXTRACTION));
        handlers.put(Subscribe.VRO, new VroHandler(Subscribe.VRO));
        handlers.put(Subscribe.KPO, new KpoHandler(Subscribe.KPO));
        handlers.put(Subscribe.LABORATORY_STORAGES, new StoragesHandler(Subscribe.LABORATORY_STORAGES));
        handlers.put(Subscribe.LABORATORY_TURNS, new TurnHandler(Subscribe.LABORATORY_TURNS));
        handlers.put(Subscribe.USERS, new UserHandler(Subscribe.USERS));
        handlers.put(Subscribe.MANUFACTURE_REPORTS, new ManufactureReportHandler(Subscribe.MANUFACTURE_REPORTS));
        handlers.put(Subscribe.STOCK, new StockHandler(Subscribe.STOCK));
        handlers.put(Subscribe.SEALS, new SealHandler(Subscribe.SEALS));
        handlers.put(Subscribe.BOARD, new BoardHandler(Subscribe.BOARD));
        handlers.put(Subscribe.ROUND_REPORT, new RoundReportHandler(Subscribe.ROUND_REPORT));
    }

    public static ActiveSubscriptions getInstance() {
        return instance;
    }

    public void subscribe(Subscribe sub, Session session, Worker worker, Role view, JsonObject args) throws IOException {
        if (sub == Subscribe.NOTIFICATIONS) {
            if (!byWorker.containsKey(worker)) {
                byWorker.put(worker, new LinkedList<>());
            }
            byWorker.get(worker).add(session);
        }else if (sub == Subscribe.MESSAGES){
            if (!byWorker.containsKey(worker)) {
                byWorker.put(worker, new LinkedList<>());
            }
            byWorker.get(worker).add(session);
            messageHandler.handle(worker, session);
        } else if (sub == Subscribe.SESSION_TIMER){
            sessionTimer.register(worker, session);
        } else {
            if (!bySubscribe.containsKey(sub)) {
                bySubscribe.put(sub, new LinkedList<>());
            }
            bySubscribe.get(sub).add(session);
            if (handlers.containsKey(sub)) {
                handlers.get(sub).handle(session, view, args);
            }
            if(!workers.containsKey(sub)){
                workers.put(sub, new LinkedList<>());
            }
            workers.get(sub).add(worker);
            views.put(session, view);
        }
    }
    public void unsubscribe(Subscribe sub, Session session){
        if (bySubscribe.containsKey(sub)){
            final LinkedList<Session> sessions = bySubscribe.get(sub);
            sessions.remove(session);
            if(sessions.size() == 0){
                bySubscribe.remove(sub);
            }
        }
    }
    public void send(Session session, String msg){
        if (session.isOpen()){
            try {
                session.getBasicRemote().sendText(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public synchronized void send(Subscribe sub, Object txt) {
        if (bySubscribe.containsKey(sub)){
            for (Session session : bySubscribe.get(sub)){
                send(session, prepareMessage(sub, txt));
            }
        }
    }
    public synchronized boolean send(Subscribe sub, Worker worker, Object message) throws IOException {
        return send(sub, worker.getId(), message);
    }

    public synchronized boolean send(Subscribe sub, int workerId, Object message) throws IOException {
        if (byWorker.containsKey(workerId)) {
            String prepareMessage = prepareMessage(sub, message);
            for (Session session : byWorker.get(workerId)){
                if(session.isOpen()) {
                    session.getBasicRemote().sendText(prepareMessage);
                }
            }
            return true;
        }
        return false;
    }

    public static String prepareMessage(Subscribe type, Object msg){
        return prepareMessage(type.toString(), msg);
    }

    public static synchronized String prepareMessage(String type, Object msg){
        JSONObject object = new JSONObject();
        object.put(TYPE, type);
        object.put(DATA, msg);
        return object.toJSONString();
    }

    public void close() {
        log.info("Close all subscribe sessions");
        saveSubscribes();
        clearSessions(bySubscribe.values());
        bySubscribe.clear();
        clearSessions(byWorker.values());
        byWorker.clear();
    }

    private void saveSubscribes() {
        //todo remove saved subscribes by user
        //todo user->subscribes
    }

    private void clearSessions(Collection<LinkedList<Session>> values) {
        for (LinkedList<Session> sessions : values) {
            sessions.stream().filter(Session::isOpen).forEach(session -> {
                try {
                    session.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public LinkedList<Worker> getSubscribeWorkers() {
        return new LinkedList<>(byWorker.keySet());
    }
    final LinkedList<Session> emptySubscribeList = new LinkedList<>();
    public LinkedList<Session> getSessions(Subscribe w) {
        if (bySubscribe.containsKey(w)){
            return bySubscribe.get(w);
        }
        return emptySubscribeList;
    }

    public Role getSessionView(Session session) {
        return views.get(session);
    }
}
