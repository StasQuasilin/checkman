package entity;

import constants.Constants;
import org.json.simple.JSONObject;
import utils.JsonPool;

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
        return toJson(0);
    }
    public JSONObject toJson(){
        return toJson(0);
    }
    public abstract JSONObject toJson(int level);
}
