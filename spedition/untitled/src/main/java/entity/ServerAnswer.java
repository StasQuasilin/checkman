package entity;

import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static constants.Keys.STATUS;

public abstract class ServerAnswer extends JsonAble {

    private HashMap<String, String> params = new HashMap<>();

    public void addParam(String key, String value){
        params.put(key, value);
    }

    abstract String getStatus();

    @Override
    public JSONObject toJson() {
        final JSONObject jsonObject = getJsonObject();
        jsonObject.put(STATUS, getStatus());
        for (Map.Entry<String, String> entry : params.entrySet()){
            jsonObject.put(entry.getKey(), entry.getValue());
        }
        return jsonObject;
    }
}
