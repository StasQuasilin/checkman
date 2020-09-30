package utils.json;

import org.json.simple.JSONObject;

public abstract class JsonAble {
    public final JSONObject getJsonObject() {
        return new JSONObject();
    }
    public abstract JSONObject toJson();

    public JSONObject shortJson() {
        return toJson();
    }
}
