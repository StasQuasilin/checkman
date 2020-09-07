package utils.json;

import org.json.simple.JSONObject;

import java.sql.Date;

public class JsonObject {

    private JSONObject json;

    public JsonObject(Object parse) {
        json = (JSONObject) parse;
    }

    public String getString(String key) {
        if (contains(key)){
            return String.valueOf(json.get(key));
        }
        return null;
    }

    public boolean contains(String key) {
        return json.containsKey(key);
    }

    @Override
    public String toString() {
        return json.toJSONString();
    }

    public int getInt(String key) {
        if (contains(key)){
            return Integer.parseInt(String.valueOf(get(key)));
        }
        return 0;
    }

    public Object get(String key) {
        return json.get(key);
    }

    public Date getDate(String key) {
        if (contains(key)){
            return Date.valueOf(String.valueOf(get(key)));
        }
        return null;
    }

    public float getFloat(String key) {
        if (contains(key)){
            return Float.parseFloat(String.valueOf(get(key)));
        }
        return 0;
    }
}
