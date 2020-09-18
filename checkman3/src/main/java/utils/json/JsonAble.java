package utils.json;

import org.json.simple.JSONObject;

public abstract class JsonAble {
    public JSONObject toShortJson(){
        return toJson();
    }
    public abstract JSONObject toJson();
    public JSONObject jsonForEditor(){ return toJson(); }
}
