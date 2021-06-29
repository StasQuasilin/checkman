package api.sockets;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import utils.json.JsonObject;

/**
 * Created by szpt_user045 on 11.07.2019.
 */
public abstract class API {
    private final JSONParser parser = new JSONParser();
    public JsonObject parseJson(String string) throws ParseException {
        return new JsonObject(parser.parse(string));
    }

}
