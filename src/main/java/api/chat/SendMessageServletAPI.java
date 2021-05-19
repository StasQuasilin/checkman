package api.chat;

import api.ServletAPI;
import constants.Branches;
import entity.Worker;
import entity.answers.Answer;
import entity.chat.Chat;
import entity.chat.ChatMember;
import entity.chat.ChatMessage;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.UpdateUtil;
import utils.answers.SuccessAnswer;
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
public class SendMessageServletAPI extends ServletAPI {

    final dbDAO dao = dbDAOService.getDAO();
    final UpdateUtil updateUtil = new UpdateUtil();
    final Logger log = Logger.getLogger(SendMessageServletAPI.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null) {
            System.out.println(body);
            long chatId = -1;
            String chatKey = String.valueOf(body.get("key"));
            if (body.containsKey("chat")){
                chatId = (long) body.get("chat");
            }
            Worker worker = getWorker(req);
            if (worker == null) {
                worker = dao.getObjectById(Worker.class, body.get("sender"));
            }
            if (worker != null) {
                Chat chat;
                List<ChatMember> members;
                if (chatId != -1) {
                    chat = dao.getChatById(chatId);
                    members = dao.getMembersByChat(chat);
                } else {
                    chat = new Chat();
                    members = new ArrayList<>();
                    boolean alreadyContainMe = false;
                    for (Object membersObject : (JSONArray) body.get("members")) {
                        JSONObject memberJson = (JSONObject) (membersObject);
                        ChatMember member = new ChatMember();
                        Worker workerById = dao.getObjectById(Worker.class, memberJson.get("id"));
                        if (workerById.getId() == worker.getId()){
                            alreadyContainMe = true;
                        }
                        member.setMember(workerById);
                        member.setChat(chat);
                        members.add(member);
                    }
                    //title
                    String title;
                    int membersCount = members.size();

                    if (membersCount > 2){
                        chat.setGroupChat(true);
                    }

                    if (body.containsKey("title")) {
                        title = String.valueOf(body.get("title"));
                    } else {
                        membersCount = membersCount > 3 ? 3 : membersCount;
                        String[] names = new String[membersCount ];
                        for (int i = 0; i < membersCount; i++) {
                            names[i] = members.get(i).getMember().getValue();
                        }
                        title = String.join(", ", names);
                    }

                    chat.setTitle(title);
                    dao.save(chat);

                    if (!alreadyContainMe) {
                        ChatMember member = new ChatMember();
                        member.setMember(worker);
                        member.setChat(chat);
                        members.add(member);
                    }

                    members.forEach(dao::save);
                }

                ChatMessage message;
                long messageId = -1;

                JSONObject messageJson = (JSONObject) body.get("message");
                if (messageJson.containsKey("id")) {
                    messageId = (long) messageJson.get("id");
                }
                if (messageId != -1) {
                    message = dao.getMessageById(messageId);
                } else {
                    message = new ChatMessage();
                    message.setChat(chat);
                    message.setTimestamp(new Timestamp(System.currentTimeMillis()));
                    message.setSender(dao.getChatMember(chat, worker));
                }

                String text = String.valueOf(messageJson.get("text"));
                message.setMessage(text);
                dao.save(message);

                Answer success = new SuccessAnswer();
                if (chatId == -1) {
                    success.add("chat", chat.getId());
                    success.add("key", chatKey);
                }
                JSONObject jsonAnswer = parser.toJson(success);
                write(resp, jsonAnswer.toJSONString());
                pool.put(jsonAnswer);
                chat.setLastMessage(message);
                for (ChatMember member : members) {
                    if (chatId == -1) {
                        updateUtil.onSave(chat, chatKey, member.getMember());
                    }
                    updateUtil.onSave(message, member.getMember());
                }
            } else {
                log.error("No worker data");
            }
        }
    }
}
