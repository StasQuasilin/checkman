package ua.quasilin.chekmanszpt.packets;

import org.json.JSONException;
import org.json.simple.parser.JSONParser;

/**
 * Created by Kvasik on 30.07.2019.
 */

public abstract class Packet {
    private final JSONParser parser = new JSONParser();
    final JsonPool pool = JsonPool.getPool();
    public abstract String toJson();
}
