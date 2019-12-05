package api.sockets.handlers;

import api.sockets.Subscriber;
import entity.DealType;

import javax.websocket.Session;
import java.io.IOException;

/**
 * Created by szpt_user045 on 05.12.2019.
 */
public class RetailHandler extends OnSubscribeHandler {
    
    public RetailHandler(Subscriber subscriber) {
        super(subscriber);
    }

    @Override
    public void handle(Session session) throws IOException {

    }
}
