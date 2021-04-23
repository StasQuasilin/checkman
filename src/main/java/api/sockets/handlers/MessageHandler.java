package api.sockets.handlers;

import api.sockets.ActiveSubscriptions;
import api.sockets.Subscribe;
import entity.Worker;
import entity.chat.Chat;
import entity.chat.ChatMember;
import entity.chat.ChatMessage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.JsonParser;
import utils.JsonPool;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;

import javax.websocket.Session;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by szpt_user045 on 29.07.2019.
 */
public class MessageHandler {

    private static final String UPDATE = "update";
    final dbDAO dao = dbDAOService.getDAO();
    final JsonPool pool= JsonPool.getPool();
    final JsonParser parser = new JsonParser();
    public static final String CONTACTS = "contacts";
    public static final String CHATS = "chats";
    public static final String MESSAGES = "messages";

    public void handle(Worker worker, Session session) throws IOException {

        JSONArray contacts = pool.getArray();
        contacts.addAll(dao.getWorkersWithout(worker).stream().map(parser::toJson).collect(Collectors.toList()));

        JSONArray chats = pool.getArray();
        for (ChatMember member : dao.getChatMembersByWorker(worker)){
            Chat chat = member.getChat();
            if (!chat.isArchive()) {
                List<ChatMessage> messagesByChat = dao.getLimitMessagesByChat(chat, 1);
                if (messagesByChat.size() > 0) {
                    chat.setLastMessage(messagesByChat.get(0));
                }
                Set<ChatMember> members = chat.getMembers();
                if (members.size() == 2) {
                    members.stream().filter(m -> m.getMember().getId() != worker.getId()).forEach(m -> chat.setTitle(m.getMember().getValue()));
                }
                chats.add(parser.toJson(chat));
            }
        }

        JSONObject data = pool.getObject();
        data.put(CONTACTS, contacts);
        data.put(CHATS, chats);

        JSONObject update = pool.getObject();
        update.put(UPDATE, data);

        session.getBasicRemote().sendText(ActiveSubscriptions.prepareMessage(Subscribe.MESSAGES, update));
        pool.put(data);
    }
}
