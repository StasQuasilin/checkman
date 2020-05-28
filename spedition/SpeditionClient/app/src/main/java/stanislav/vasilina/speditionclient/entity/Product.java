package stanislav.vasilina.speditionclient.entity;

import androidx.annotation.NonNull;

import org.json.simple.JSONObject;

import static stanislav.vasilina.speditionclient.constants.Keys.ID;
import static stanislav.vasilina.speditionclient.constants.Keys.NAME;

public class Product extends JsonAble{
    private int id;
    private String name;

    public Product(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(ID, id);
        jsonObject.put(NAME, name);
        return jsonObject;
    }
}
