package utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by szpt_user045 on 25.06.2019.
 */
public class JsonPool {
    private static final JsonPool pool = new JsonPool();

    public static JsonPool getPool() {
        return pool;
    }

    private ArrayList<JSONObject> objects = new ArrayList<>();
    private ArrayList<JSONArray> arrays = new ArrayList<>();

    public void put(JSONObject json){
        for (Object o : json.values()){
            if (o instanceof JSONObject){
                put((JSONObject) o);
            } else if (o instanceof JSONArray){
                put((JSONArray) o);
            }
        }
        json.clear();
        if(!objects.contains(json)) {
            objects.add(json);
        }

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
        if (array.contains(array)) {
            arrays.add(array);
        }
    }

    public synchronized JSONObject getObject(){
        if (objects.size() > 0){
            return objects.remove(0);
        } else {
            return new JSONObject();
        }
    }

    public synchronized JSONArray getArray(){
        if (arrays.size() > 0) {
            return arrays.remove(0);
        } else {
            return new JSONArray();
        }
    }

}