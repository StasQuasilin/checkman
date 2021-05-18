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
import java.util.Collection;
import java.util.HashMap;

/**
 * Created by szpt_user045 on 11.07.2019.
 */
public class ActiveSubscriptions {

    private final dbDAO dao = dbDAOService.getDAO();
    private static final ActiveSubscriptions instance = new ActiveSubscriptions();
    private final Logger log = Logger.getLogger(ActiveSubscriptions.class);
    final HashMap<Subscribe, OnSubscribeHandler> handlers = new HashMap<>();
    final HashMap<Subscribe, ArrayList<Session>> bySubscribe = new HashMap<>();
    final HashMap<Integer, ArrayList<Session>> byWorker = new HashMap<>();
    final MessageHandler messageHandler = new MessageHandler();
    final SessionTimer sessionTimer = SessionTimer.getInstance();
    public static final JsonPool pool = JsonPool.getPool();
    public static final String TYPE = "type";
    private static final String DATA = "data";

    private ActiveSubscriptions(){
        for(Subscribe s : Subscribe.values()){
            bySubscribe.put(s, new ArrayList<>());
        }
        handlers.put(Subscribe.CONTRACTS_BUY, new ContractHandler(DealType.buy, Subscribe.CONTRACTS_BUY));
        handlers.put(Subscribe.CONTRACTS_SELL, new ContractHandler(DealType.sell, Subscribe.CONTRACTS_SELL));
        handlers.put(Subscribe.DEAL_BUY, new DealHandler(DealType.buy, Subscribe.DEAL_BUY));
        handlers.put(Subscribe.DEAL_BUY_ARCHIVE, new DealArchiveHandler(DealType.buy, Subscribe.DEAL_BUY_ARCHIVE));
        handlers.put(Subscribe.DEAL_SELL, new DealHandler(DealType.sell, Subscribe.DEAL_SELL));
        handlers.put(Subscribe.DEAL_SELL_ARCHIVE, new DealArchiveHandler(DealType.sell, Subscribe.DEAL_SELL_ARCHIVE));
        handlers.put(Subscribe.LOGISTIC, new LogisticHandler(Subscribe.LOGISTIC));
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

    public void subscribe(Subscribe sub, Session session, long workerId) throws IOException {
        Worker worker = dao.getObjectById(Worker.class, workerId);
        if (sub == Subscribe.NOTIFICATIONS) {
            if (!byWorker.containsKey(worker.getId())) {
                byWorker.put(worker.getId(), new ArrayList<>());
                byWorker.get(worker.getId()).add(session);
            }

        }else if (sub == Subscribe.MESSAGES){
            if (!byWorker.containsKey(worker.getId())) {
                byWorker.put(worker.getId(), new ArrayList<>());
                byWorker.get(worker.getId()).add(session);
            }
            messageHandler.handle(worker, session);
        } else if (sub == Subscribe.SESSION_TIMER){
            sessionTimer.register(worker, session);
        } else {
            bySubscribe.get(sub).add(session);
            if (handlers.containsKey(sub)) {
                handlers.get(sub).handle(session, worker);
            }
        }
    }
    public void unsubscribe(Subscribe sub, Session session){
        bySubscribe.get(sub).remove(session);
    }

    public synchronized void send(Subscribe sub, Object txt) {
        String prepareMessage = prepareMessage(sub, txt);
        for (Session session : bySubscribe.get(sub)){
            if (session.isOpen()){
                try {
                    session.getBasicRemote().sendText(prepareMessage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
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

    private void clearSessions(Collection<ArrayList<Session>> values) {
        for (ArrayList<Session> sessions : values) {
            sessions.stream().filter(Session::isOpen).forEach(session -> {
                try {
                    session.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public ArrayList<Integer> getSubscribeWorkers() {
        return new ArrayList<>(byWorker.keySet());
    }
}
