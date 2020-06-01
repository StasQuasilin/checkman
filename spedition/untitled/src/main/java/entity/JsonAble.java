package entity;

import org.json.simple.JSONObject;

public abstract class JsonAble {
    public JSONObject getJsonObject(){
        return new JSONObject();
    }
    public abstract JSONObject toJson();
}
