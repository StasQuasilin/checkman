package utils.notifications;

import api.sockets.ActiveSubscriptions;
import api.sockets.Subscriber;
import entity.Worker;

import java.io.IOException;
import java.util.List;

/**
 * Created by szpt_user045 on 24.01.2020.
 */
public class Notificator {

    ActiveSubscriptions subscriptions = ActiveSubscriptions.getInstance();
    public void sendNotification(List<Worker> workerList, Object notification){
        if (workerList.size() > 0){
            for (Worker worker : workerList){
                sendNotification(worker, notification);
            }
        }
    }

    public void sendNotification(Worker worker, Object notification){
        try {
            subscriptions.send(Subscriber.NOTIFICATIONS, worker, notification);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendNotificationFrom(Worker sender, Object notification) throws IOException {
        for (int id : subscriptions.getSubscribeWorkers()){
            if (sender.getId() != id){
                subscriptions.send(Subscriber.NOTIFICATIONS, id, notification);
            }
        }
    }
}
