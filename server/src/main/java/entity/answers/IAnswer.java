package entity.answers;

import entity.JsonAble;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by szpt_user045 on 19.02.2019.
 */
public abstract class IAnswer extends JsonAble {

    public abstract String status();
    private HashMap<String, Object> params;

    public IAnswer() {
        params = new HashMap<>();
    }

    public void add(String key, Object value){
        params.put(key, value);
    }

    public HashMap<String, Object> getParams() {
        return params;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = pool.getObject();
        json.put(STATUS, status());
        for (Map.Entry<String, Object> entry : getParams().entrySet()){
            json.put(entry.getKey(), entry.getValue());
        }
        return json;
    }
}
