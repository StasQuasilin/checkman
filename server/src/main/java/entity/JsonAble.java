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
    public static final JSONObject EMPTY_OBJECT = pool.getObject();
    static {
        EMPTY_OBJECT.put(ID, -1);

    }

    public JSONObject toShortJson(){
        return toJson();
    }
    public abstract JSONObject toJson();
}
