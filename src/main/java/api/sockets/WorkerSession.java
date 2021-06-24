package api.sockets;

import entity.Worker;

import javax.websocket.Session;

public class WorkerSession {
    private final Worker worker;
    private final Session session;

    WorkerSession(Worker worker, Session session){
        this.worker = worker;
        this.session = session;
    }
}
