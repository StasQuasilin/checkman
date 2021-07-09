package api.sockets.handlers;

import api.sockets.Subscribe;
import entity.Role;
import utils.JsonParser;
import utils.JsonPool;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;
import utils.json.JsonObject;

import javax.websocket.Session;
import java.io.IOException;

/**
 * Created by szpt_user045 on 11.07.2019.
 */
public abstract class OnSubscribeHandler {

    public static final String ADD = "add";
    final dbDAO dao = dbDAOService.getDAO();
    final JsonPool pool = JsonPool.getPool();

    public abstract void handle(Session session, Role view, JsonObject args) throws IOException;
    public final JsonParser parser = new JsonParser();
    public final Subscribe subscribe;

    protected OnSubscribeHandler(Subscribe subscribe) {
        this.subscribe = subscribe;
    }

    public static final int NO_PRICE = 1;
    public static final int NO_ANALYSES = 2;
    public static final int NO_ONE = 3;
    public static final int HIDDEN_NAMES = 4;

    public static int calculateSecureMask(Role role){
        if (role == Role.security){
            return NO_ONE;
        } else if (role == Role.analyser || role == Role.warehousing){
            return NO_PRICE;
        } else if (role == Role.secure){
            return HIDDEN_NAMES;
        }
        return 0;
    }

}
