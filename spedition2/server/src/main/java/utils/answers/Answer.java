package utils.answers;

import org.json.simple.JSONObject;
import utils.json.JsonAble;

import java.util.HashMap;
import java.util.Map;

import static constants.Keys.STATUS;

public abstract class Answer extends JsonAble {

    abstract String getStatus();
    private final HashMap<String, Object> attributes = new HashMap<>();
    public void addAttribute(String key, Object value){
        attributes.put(key, value);
    }

    @Override
    public JSONObject toJson() {
        final JSONObject jsonObject = getJsonObject();
        jsonObject.put(STATUS, getStatus());
        for (Map.Entry<String, Object> entry : attributes.entrySet()){
            jsonObject.put(entry.getKey(), entry.getValue());
        }
        return jsonObject;
    }
}
