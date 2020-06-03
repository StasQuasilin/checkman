package api.socket;

import constants.ApiLinks;
import org.json.simple.JSONObject;
import utils.JsonParser;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

import static constants.Keys.ACTION;
import static constants.Keys.SUBSCRIBE;

@ServerEndpoint(ApiLinks.SOCKET)
public class SocketEndpoint {

    private final Subscribes subscribes = Subscribes.getInstance();
    private final JsonParser parser = new JsonParser();

    @OnOpen
    public void OnOpen(Session session, EndpointConfig config){
        System.out.println("Connect " + session.getId());
    }

    @OnMessage
    public void OnMessage(Session session, String message){
        final JSONObject parse = parser.parse(message);
        if (parse != null){
            if (parse.containsKey(ACTION)){
                SocketAction action = SocketAction.valueOf(String.valueOf(parse.get(ACTION)));
                SubscribeType subscribeType = SubscribeType.valueOf(String.valueOf(parse.get(SUBSCRIBE)));
                switch (action){
                    case subscribe:
                        subscribes.add(session, subscribeType);
                        break;
                    case unsubscribe:
                        subscribes.remove(session, subscribeType);
                }
            }
        }
        System.out.println("Message: " + message);
    }

    @OnClose
    public void OnClose(Session session){
        System.out.println("Close session #" + session.getId());
        subscribes.remove(session);
    }

    @OnError
    public void OnError(Session session, Throwable cause){
        System.err.println("Error in session: " + session.getId() + ", cause: " + session.isOpen());
        cause.printStackTrace();
    }
}
