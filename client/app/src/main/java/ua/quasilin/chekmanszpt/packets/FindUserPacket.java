package ua.quasilin.chekmanszpt.packets;

import org.json.simple.JSONObject;

/**
 * Created by szpt_user045 on 01.08.2019.
 */

public class FindUserPacket extends Packet {

    private final String key;

    public FindUserPacket(String key) {
        this.key = key;
    }

    @Override
    public String toJson() {
        JSONObject json = pool.getObject();
        json.put("key", key);
        String result = json.toJSONString();
        pool.put(json);
        return result;
    }
}
