package ua.quasilin.chekmanszpt.utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Created by Kvasik on 30.07.2019.
 */

public final class JsonParser {
    private static final JSONParser parser = new JSONParser();
    public static synchronized JSONObject parse(String json){
        try {
            return (JSONObject) parser.parse(json);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
