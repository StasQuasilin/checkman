package utils.json;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.HashSet;

public class JsonObject {
    private final JSONObject jsonObject;
    public JsonObject(JSONObject jsonObject){
        this.jsonObject = jsonObject;
    }

    public JsonObject(Object o) {
        this((JSONObject)o);
    }

    public boolean contain(String key){
        return jsonObject.containsKey(key);
    }

    public int getInt(String key) {
        final String string = getString(key);
        if (string != null){
            return Integer.parseInt(string);
        }
        return 0;
    }

    public String getString(String key) {
        if(jsonObject.containsKey(key)){
            return String.valueOf(jsonObject.get(key));
        }
        return null;
    }

    @Override
    public String toString() {
        return jsonObject.toJSONString();
    }

    public JSONArray getArray(String key) {
        if (contain(key)){
            return (JSONArray) jsonObject.get(key);
        }
        return null;
    }

    public Object get(Object key) {
        return jsonObject.get(key);
    }

    public float getFloat(String key) {
        final String string = getString(key);
        if (string != null && !string.isEmpty()){
            return Float.parseFloat(string);
        }
        return 0;
    }

    public boolean getBool(String key) {
        final String string = getString(key);
        if (string != null && !string.isEmpty()){
            return Boolean.parseBoolean(string);
        }
        return false;
    }

    public JsonObject getObject(String key) {
        if (contain(key)){
            return new JsonObject(get(key));
        }
        return null;
    }

    public HashSet<Object> keySet() {
        return new HashSet<Object>(jsonObject.keySet());
    }
}
