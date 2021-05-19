package utils.json;

import org.json.simple.JSONObject;

public class JsonObject {
    private final JSONObject jsonObject;
    public JsonObject(JSONObject jsonObject){
        this.jsonObject = jsonObject;
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

    private String getString(String key) {
        if(jsonObject.containsKey(key)){
            return String.valueOf(jsonObject.get(key));
        }
        return null;
    }
}
