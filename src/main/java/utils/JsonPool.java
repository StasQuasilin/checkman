package utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.LinkedList;

/**
 * Created by szpt_user045 on 25.06.2019.
 */
public class JsonPool {
    private static final JsonPool pool = new JsonPool();

    public static JsonPool getPool() {
        return pool;
    }

    private LinkedList<JSONObject> objects = new LinkedList<>();
    private LinkedList<JSONArray> arrays = new LinkedList<>();

    public void put(JSONObject json){
        for (Object o : json.values()){
            if (o instanceof JSONObject){
                put((JSONObject) o);
            } else if (o instanceof JSONArray){
                put((JSONArray) o);
            }
        }
        json.clear();
        objects.add(json);
    }

    public void put(JSONArray array) {
        for (Object o : array){
            if (o instanceof JSONObject){
                put((JSONObject) o);
            } else if (o instanceof JSONArray){
                put((JSONArray) o);
            }
        }
        array.clear();
        arrays.add(array);
    }

    public JSONObject getObject(){
        if (objects.size() > 0){
            return objects.remove(0);
        } else {
            return new JSONObject();
        }
    }

    public JSONArray getArray(){
        if (arrays.size() > 0) {
            return arrays.remove(0);
        } else {
            return new JSONArray();
        }
    }

}
