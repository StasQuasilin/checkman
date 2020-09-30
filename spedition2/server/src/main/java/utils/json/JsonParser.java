package utils.json;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.Reader;

public class JsonParser {
    public JSONParser parser = new JSONParser();

    public JsonObject parse(String json){
        try {
            return new JsonObject(parser.parse(json));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public JsonObject parse(Reader reader){
        try {
            return new JsonObject(parser.parse(reader));
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
