package utils.answer;

import org.json.simple.JSONObject;
import utils.json.JsonAble;

import java.util.HashMap;

import static constants.Keys.STATUS;

public abstract class Answer extends JsonAble {
    private final HashMap<String, Object> params = new HashMap<>();
    public void addAttribute(String key, Object value){
        params.put(key, value);
    }
    abstract String getStatus();

    @Override
    public JSONObject toJson() {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put(STATUS, getStatus());
        jsonObject.putAll(params);
        return jsonObject;
    }
}
