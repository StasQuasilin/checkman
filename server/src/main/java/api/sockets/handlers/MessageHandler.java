package api.sockets.handlers;

import api.sockets.ActiveSubscriptions;
import api.sockets.Subscriber;
import entity.Worker;
import entity.chat.Chat;
import entity.chat.ChatMember;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.JsonParser;
import utils.JsonPool;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by szpt_user045 on 29.07.2019.
 */
public class MessageHandler {

    final dbDAO dao = dbDAOService.getDAO();
    final JsonPool pool= JsonPool.getPool();
    final JsonParser parser = new JsonParser();

    public void handle(Worker worker, Session session) throws IOException {

        JSONArray contacts = pool.getArray();
        contacts.addAll(dao.getWorkersWithout(worker).stream().map(parser::toJson).collect(Collectors.toList()));

        JSONArray chats = pool.getArray();
        for (ChatMember member : dao.getChatMembersByWorker(worker)){
            Chat chat = member.getChat();
            Set<ChatMember> members = chat.getMembers();
            if (members.size() == 2){
                members.stream().filter(m -> m.getMember().getId() != worker.getId()).forEach(m -> {
                    chat.setTitle(m.getMember().getValue());
                });
            }
            chats.add(parser.toJson(chat, dao.getLimitMessagesByChat(chat)));

        }

        JSONObject data = pool.getObject();
        data.put("contacts", contacts);
        data.put("chats", chats);
        JSONObject json = pool.getObject();
        json.put("add", data);


        session.getBasicRemote().sendText(ActiveSubscriptions.prepareMessage(Subscriber.MESSAGES, json));
        pool.put(data);
    }
}
