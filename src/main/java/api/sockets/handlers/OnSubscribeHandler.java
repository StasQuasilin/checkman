package api.sockets.handlers;

import api.sockets.Subscribe;
import entity.Role;
import entity.Worker;
import utils.JsonParser;
import utils.JsonPool;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;

import javax.websocket.Session;
import java.io.IOException;

/**
 * Created by szpt_user045 on 11.07.2019.
 */
public abstract class OnSubscribeHandler {

    public static final String ADD = "add";
    final dbDAO dao = dbDAOService.getDAO();
    final JsonPool pool = JsonPool.getPool();

    public abstract void handle(Session session, Worker worker) throws IOException;
    public final JsonParser parser = new JsonParser();
    public final Subscribe subscribe;

    protected OnSubscribeHandler(Subscribe subscribe) {
        this.subscribe = subscribe;
    }

    public static final int NO_PRICE = 1;
    public static final int NO_ANALYSES = 2;
    public static final int NO_ONE = 3;

    public int calculateSecureMask(Worker worker){
        final Role role = worker.getRole();
        if (role == Role.security || role == Role.warehousing){
            return NO_ONE;
        } else if (role == Role.analyser){
            return NO_PRICE;
        }
        return 0;
    }

}
