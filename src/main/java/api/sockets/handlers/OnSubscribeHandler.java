package api.sockets.handlers;

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

    final dbDAO dao = dbDAOService.getDAO();
    public abstract void handle(Session session) throws IOException;
    public final JsonParser parser = new JsonParser();

}
