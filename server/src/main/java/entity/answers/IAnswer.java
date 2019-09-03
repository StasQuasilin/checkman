package entity.answers;

import java.util.HashMap;

/**
 * Created by szpt_user045 on 19.02.2019.
 */
public abstract class IAnswer {

    public abstract String status();
    private HashMap<String, Object> params;

    public IAnswer() {
        params = new HashMap<>();
    }

    public void add(String key, Object value){
        params.put(key, value);
    }

    public HashMap<String, Object> getParams() {
        return params;
    }
}