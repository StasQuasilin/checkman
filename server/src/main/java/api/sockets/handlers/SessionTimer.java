package api.sockets.handlers;

import api.sockets.ActiveSubscriptions;
import api.sockets.Subscriber;
import entity.Role;
import entity.Worker;
import org.apache.log4j.Logger;
import utils.access.UserBox;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;

import javax.websocket.Session;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    dbDAO dao = dbDAOService.getDAO();
    public static final LocalTime TARGET_1 = LocalTime.of(8, 0);
    public static final LocalTime TARGET_2 = LocalTime.of(20, 0);

    final static UserBox userBox = UserBox.getUserBox();
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
    Timer master;
    private void initTimer() {
        if (master != null && master.isRunning()){
            master.stop();
        }
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime target = getNextDateTime();
        log.info("Next session check at " + target.toString());

        long delay = target.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() -
                now.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        master = new Timer((int) delay, e -> checkSessions(target.toLocalTime()));
        master.setRepeats(false);
        master.start();
    }

    private void checkSessions(LocalTime localTime) {

        for (Map.Entry<Worker, Timer> entry : timerHashMap.entrySet()){
            Worker worker = entry.getKey();
            Role role = worker.getRole();
            if (localTime.getHour() == TARGET_1.getHour()){
                if (role == Role.security ){
                    close(worker);
                }
            } else if (role == Role.weigher || role == Role.analyser){
                close(worker);
            }
        }
        initTimer();
    }

    private void close(Worker worker) {
        close(worker, sessionHashMap.get(worker));
    }

    private LocalDateTime getNextDateTime(){
        LocalTime nextTime = getNextTime();
        LocalTime now = LocalTime.now();

        return LocalDateTime.now().plusSeconds(nextTime.toSecondOfDay() - now.toSecondOfDay());
    }

    private LocalTime getNextTime() {
        LocalTime now = LocalTime.now();
        if (now.isAfter(TARGET_1)){
            return TARGET_2;
        } else {
            return TARGET_1;
        }
    }

    public void register(Worker worker, Session session) {
        Timer timer = new Timer(SESSION_DELAY, e -> {
            System.out.println("Close session for: " + worker.getPerson().getValue());
            close(worker, session);
        });
        timer.setRepeats(false);
        timer.start();
        timerHashMap.put(worker, timer);
        sessionHashMap.put(worker, session);
    }

    private void close(Worker worker, Session session) {
        if(session.isOpen()) {
            String closed = ActiveSubscriptions.prepareMessage(Subscriber.SESSION_TIMER, "CLOSED");
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
        System.out.println("Update timer for " + worker.getValue());
        Timer timer = timerHashMap.get(worker);
        System.out.println("Timer: " + timer);
        if (timer != null && timer.isRunning()){
            timer.setDelay(SESSION_DELAY);
            System.out.println("Timer delay: " + timer.getDelay());
        }
    }

    public void remove(Worker worker) {
        Timer remove = timerHashMap.remove(worker);
        if(remove != null && remove.isRunning()){
            remove.stop();
        }
    }
}
