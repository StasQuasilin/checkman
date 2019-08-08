package ua.quasilin.chekmanszpt.packets;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

/**
 * Created by Kvasik on 30.07.2019.
 */

public class JsonPool {
    private static final JsonPool pool = new JsonPool();

    public static JsonPool getPool() {
        return pool;
    }

    private JsonPool() {}

    private final ArrayList<JSONObject> objects = new ArrayList<>();
    private final ArrayList<JSONArray> arrays = new ArrayList<>();

    public synchronized JSONObject getObject(){
        JSONObject object;
        if (objects.size() > 0){
            object = objects.get(0);
            objects.remove(object);
        } else {
            object = new JSONObject();
        }
        return object;
    }

    public void put(JSONObject json) {
        for (Object o : json.values()){
            if(o instanceof JSONObject){
                put((JSONObject) o);
            } else if (o instanceof JSONArray){
                put((JSONArray)o);
            }
        }
        json.clear();
        objects.add(json);
    }

    private void put(JSONArray o) {
        arrays.add(o);
    }

    public synchronized JSONArray getArray() {
        JSONArray array;
        if (arrays.size() == 0) {
            array = new JSONArray();
        } else {
            array = arrays.get(0);
            arrays.remove(array);
        }
        return array;
    }
}
