package entity;

import constants.Constants;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.JsonPool;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by szpt_user045 on 08.11.2019.
 */
public abstract class JsonAble implements Constants{

    public static JsonPool pool = JsonPool.getPool();
    public abstract JSONObject toJson();
    public JSONArray toJson(Set<JsonAble> set){
        JSONArray array = pool.getArray();
        array.addAll(set.stream().map(JsonAble::toJson).collect(Collectors.toList()));
        return array;
    }
}
