package utils.json;

import org.json.simple.JSONObject;

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
            return Integer.parseInt(getString(key));
        }
        return 0;
    }

    public Object get(String key) {
        return json.get(key);
    }
}
