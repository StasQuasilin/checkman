package ua.quasilin.chekmanszpt.packets;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import ua.quasilin.chekmanszpt.entity.Chat;
import ua.quasilin.chekmanszpt.entity.ChatContainer;
import ua.quasilin.chekmanszpt.entity.ChatMessage;
import ua.quasilin.chekmanszpt.entity.SimpleChatMessage;
import ua.quasilin.chekmanszpt.entity.Worker;

/**
 * Created by szpt_user045 on 06.08.2019.
 */

public class SendMessagePacket extends Packet {

    private final Chat chat;
    private final SimpleChatMessage message;

    public SendMessagePacket(Chat chat, SimpleChatMessage message) {
        super();
        this.chat = chat;
        this.message = message;
    }

    @Override
    public String toJson() {
        JSONObject messageObject = pool.getObject();
        messageObject.put("id", message.getId());
        messageObject.put("text", message.getText());

        JSONObject object = pool.getObject();
        object.put("chat", chat.getId());
        object.put("key", chat.getKey());
        object.put("message", messageObject);
        object.put("sender", ChatContainer.worker.getId());
        if (chat.getId() == -1){
            JSONArray array = pool.getArray();
            for (Worker worker : chat.getMembers()){
                if (worker.getId() != ChatContainer.worker.getId()) {
                    array.add(toJson(worker));
                }
            }
            object.put("members", array);
        }
        String result = object.toJSONString();
        pool.put(object);
        System.out.println(result);
        return result;
    }

    private JSONObject toJson(Worker worker) {
        JSONObject json = pool.getObject();
        json.put("id", worker.getId());
        return json;
    }
}
