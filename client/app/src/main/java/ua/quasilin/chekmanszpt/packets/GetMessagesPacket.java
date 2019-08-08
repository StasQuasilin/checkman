package ua.quasilin.chekmanszpt.packets;

import org.json.simple.JSONObject;

/**
 * Created by szpt_user045 on 06.08.2019.
 */

public class GetMessagesPacket extends Packet {
    private final long chatId;
    public GetMessagesPacket(long chatId) {
        super();
        this.chatId = chatId;
    }

    @Override
    public String toJson() {
        JSONObject object = pool.getObject();
        object.put("chat", chatId);
        String result = object.toJSONString();
        pool.put(object);
        return result;
    }
}
