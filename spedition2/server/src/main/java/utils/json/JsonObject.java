package utils.json;

import org.json.simple.JSONObject;

import java.sql.Date;
import java.sql.Timestamp;

public class JsonObject {

    final JSONObject json;

    public JsonObject(Object parsed) {
        json = (JSONObject) parsed;
    }

    public String getString(String key){
        if (json.containsKey(key)){
            return String.valueOf(json.get(key));
        }
        return null;
    }

    public boolean containKey(String key) {
        return json.containsKey(key);
    }

    @Override
    public String toString() {
        return json.toJSONString();
    }

    public Object get(String key) {
        return json.get(key);
    }

    public float getFloat(String name) {
        if (containKey(name)){
            final String string = getString(name);
            if (string.isEmpty()){
                return 0;
            }
            return Float.parseFloat(string);
        }
        return 0;
    }

    public int getInt(String name) {
        if (containKey(name)){
            return Integer.parseInt(String.valueOf(json.get(name)));
        }
        return 0;
    }

    public Timestamp getTimestamp(String date) {
        if (containKey(date)){
            return Timestamp.valueOf(getString(date));
        }
        return null;
    }

    public boolean getBoolean(String name) {
        if (containKey(name)){
            return (boolean) json.get(name);
        }
        return false;
    }

    public long getLong(String name) {
        if (containKey(name)){
            return Long.parseLong(getString(name)) ;
        }
        return 0;
    }

    public Date getDate(String key) {
        if (containKey(key)){
            return Date.valueOf(getString(key));
        }
        return null;
    }
}
