package api.sockets;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Created by szpt_user045 on 11.07.2019.
 */
public abstract class API {
    private JSONParser parser = new JSONParser();
    public Object parseJson(String string) throws ParseException {
        return parser.parse(string);
    }

}
