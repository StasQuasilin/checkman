package api.sockets.handlers;

import api.sockets.ActiveSubscriptions;
import api.sockets.Subscriber;
import entity.Role;
import entity.Worker;
import org.apache.log4j.Logger;
import utils.LanguageBase;
import utils.TurnDateTime;
import utils.access.UserBox;
import utils.turns.TurnBox;

import javax.websocket.Session;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
/**
 * Created by szpt_user045 on 02.12.2019.
 */
public class SessionTimer {

    private final Logger log = Logger.getLogger(SessionTimer.class);
    private final LanguageBase lb = LanguageBase.getBase();
    public static final LocalTime TARGET_1 = LocalTime.of(8, 0);
    public static final LocalTime TARGET_2 = LocalTime.of(20, 0);

    final static UserBox userBox = UserBox.getInstance();
    public static final SessionTimer instance = new SessionTimer();
    public static SessionTimer getInstance() {
        return instance;
    }

    private HashMap<Worker, Session> sessionHashMap = new HashMap<>();
    private HashMap<Worker, Timer> timerHashMap = new HashMap<>();

    public static final int SESSION_DELAY = 4 * 60 * 60 * 1000;

    private SessionTimer() {
        initTimer();
    }
    Timer sessionTimer;
    private void initTimer() {
        stopSessionTimer();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime target = getNextDateTime();
        if (!now.equals(target)) {

            long delay = target.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() -
                    now.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
            sessionTimer = new Timer((int) delay, e -> checkSessions(target.toLocalTime()));
            sessionTimer.setRepeats(false);
            sessionTimer.start();
        }
    }

    private void checkSessions(LocalTime localTime) {
        for (Map.Entry<Worker, Timer> entry : timerHashMap.entrySet()){
            Worker worker = entry.getKey();
            Role role = worker.getRole();

            if (role == Role.weigher || role == Role.analyser || (role == Role.admin && localTime.getHour() == TARGET_1.getHour()) ||
                    (role == Role.security && localTime.getHour() == TARGET_1.getHour())){
                close(worker);
            }
        }
        initTimer();
    }

    private void close(Worker worker) {
        close(worker, sessionHashMap.get(worker), REASON_2);
    }

    private LocalDateTime getNextDateTime(){
        TurnDateTime turnDate = TurnBox.getTurnDate(LocalDateTime.now());
        return turnDate.getEnd();
    }

    public static final String REASON_1 = "session.timeout";
    public static final String REASON_2 = "session.turnout";

    public void register(Worker worker, Session session) {
        Timer timer = new Timer(SESSION_DELAY, e -> {
            close(worker, session, REASON_1);
        });
        timer.setRepeats(false);
        timer.start();
        timerHashMap.put(worker, timer);
        sessionHashMap.put(worker, session);
    }

    private void close(Worker worker, Session session, String reason) {
        System.out.println("Close session for: " + worker.getPerson().getValue() + ", cause '" + reason + "'");
        if(session.isOpen()) {
            String closed = ActiveSubscriptions.prepareMessage(Subscriber.SESSION_TIMER, lb.get(worker.getLanguage(), reason));
            try {
                session.getBasicRemote().sendText(closed);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            remove(worker);
            userBox.remove(worker);
        }
    }

    public void update(Worker worker){
        Timer timer = timerHashMap.get(worker);
        if (timer != null && timer.isRunning()){
            timer.setDelay(SESSION_DELAY);
        }
    }

    public void remove(Worker worker) {
        if (sessionHashMap.containsKey(worker)) {
            sessionHashMap.remove(worker);
            Timer remove = timerHashMap.remove(worker);
            if (remove != null && remove.isRunning()) {
                remove.stop();
            }
        }
    }

    private void stopSessionTimer(){
        if (sessionTimer != null && sessionTimer.isRunning()){
            sessionTimer.stop();
        }
    }

    public void stop(){
        stopSessionTimer();
        timerHashMap.values().forEach(Timer::stop);
        timerHashMap.clear();
        sessionHashMap.clear();
    }
}
