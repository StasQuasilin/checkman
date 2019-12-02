package api.sockets.handlers;

import api.sockets.ActiveSubscriptions;
import api.sockets.Subscriber;
import entity.Worker;

import javax.websocket.Session;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
import javax.swing.*;
/**
 * Created by szpt_user045 on 02.12.2019.
 */
public class SessionTimer {
    public static final SessionTimer instance = new SessionTimer();

    public static SessionTimer getInstance() {
        return instance;
    }

    private HashMap<Worker, Session> workerSessionHashMap = new HashMap<>();
    private HashMap<Worker, Timer> timerHashMap = new HashMap<>();
    public static final int SESSION_DELAY = 60 * 1000;//4 * 60 * 60 * 1000;

    public void register(Worker worker, Session session) {
        Timer timer = new Timer(SESSION_DELAY, e -> {
            System.out.println(worker.getPerson().getValue());
            String closed = ActiveSubscriptions.prepareMessage(Subscriber.SESSION_TIMER, "CLOSED");
            try {
                session.getBasicRemote().sendText(closed);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        timer.setRepeats(false);
        timer.start();
        timerHashMap.put(worker, timer);
    }
    public void update(Worker worker){
        System.out.println("SESSION CONTINUE");
        Timer timer = timerHashMap.get(worker);
        if (timer != null && timer.isRunning()){
            timer.setDelay(SESSION_DELAY);
        }

    }
}
