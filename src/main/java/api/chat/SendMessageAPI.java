package api.chat;

import api.ServletAPI;
import constants.Branches;
import entity.chat.Chat;
import entity.chat.ChatMember;
import entity.chat.ChatMessage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.UpdateUtil;
import utils.hibernate.dbDAO;
import utils.hibernate.dbDAOService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by szpt_user045 on 29.07.2019.
 */
@WebServlet(Branches.API.CHAT_SEND)
public class SendMessageAPI extends ServletAPI {

    final dbDAO dao = dbDAOService.getDAO();
    final UpdateUtil updateUtil = new UpdateUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            System.out.println(body);
            long chatId = -1;
            if (body.containsKey("chat")){
                chatId = (long) body.get("chat");
            }
            Chat chat;
            List<ChatMember> members;
            if (chatId != -1){
                chat = dao.getChatById(chatId);
                members = dao.getMembersByChat(chat);
            } else {
                chat = new Chat();
                members = new ArrayList<>();
                for (Object membersObject : (JSONArray)body.get("members")){
                    JSONObject memberJson = (JSONObject) (membersObject);
                    ChatMember member = new ChatMember();
                    member.setMember(dao.getWorkerById(memberJson.get("id")));
                    member.setChat(chat);
                    members.add(member);
                }
                //title
                String title;
                if(body.containsKey("title")){
                    title = String.valueOf(body.get("title"));
                } else {
                    int membersCount = members.size() > 3 ? 3 : members.size();
                    String[] names = new String[membersCount];
                    for (int i = 0; i < membersCount; i++){
                        names[i] = members.get(i).getMember().getValue();
                    }
                    title = String.join(", ", names);
                    if (membersCount < members.size()){
                        title += "...";
                    }
                }
                chat.setTitle(title);
                dao.save(chat);
                ChatMember member = new ChatMember();
                member.setMember(getWorker(req));
                member.setChat(chat);
                members.add(member);
                members.forEach(dao::save);
            }

            ChatMessage message;
            long messageId = -1;
            JSONObject messageJson = (JSONObject) body.get("message");
            if (messageJson.containsKey("id")){
                messageId = (long) messageJson.get("id");
            }
            if (messageId != -1){
                message = dao.getMessageById(messageId);
            } else {
                message = new ChatMessage();
                message.setChat(chat);
                message.setTimestamp(new Timestamp(System.currentTimeMillis()));
                message.setSender(dao.getChatMember(chat, getWorker(req)));
            }

            String text = String.valueOf(messageJson.get("text"));
            message.setMessage(text);
            dao.save(message);
            for (ChatMember member : members) {
                updateUtil.onSave(message, member.getMember());
            }

            write(resp, answer);
        }
    }
}
