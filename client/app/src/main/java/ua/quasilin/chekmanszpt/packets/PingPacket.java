package ua.quasilin.chekmanszpt.packets;

import org.json.simple.JSONObject;

/**
 * Created by szpt_user045 on 31.07.2019.
 */

public class PingPacket extends Packet {

    private final long time;
    public PingPacket() {
        time = System.currentTimeMillis();
    }

    @Override
    public String toJson() {
        JSONObject json = pool.getObject();
        json.put("time", time);
        String result = json.toJSONString();
        pool.put(json);
        return result;
    }
}
