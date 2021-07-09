package api.sockets.handlers;

import api.sockets.ActiveSubscriptions;
import api.sockets.Subscribe;
import entity.Role;
import entity.border.BoardItem;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.json.JsonObject;

import javax.websocket.Session;
import java.io.IOException;
import java.util.List;

public class BoardHandler extends OnSubscribeHandler {

    @Override
    public void handle(Session session, Role view, JsonObject args) throws IOException {
        List<BoardItem> items = dao.getObjects(BoardItem.class);
        JSONObject json = pool.getObject();
        JSONArray array = pool.getArray();
        for (BoardItem item : items){
            array.add(item.toJson());
        }
        json.put(ADD, array);
        session.getBasicRemote().sendText(ActiveSubscriptions.prepareMessage(subscribe, json));
        pool.put(json);
    }

    public BoardHandler(Subscribe subscribe) {
        super(subscribe);
    }
}
