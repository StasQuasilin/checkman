package entity.products;

import entity.JsonAble;
import entity.weight.Unit;
import org.json.simple.JSONObject;

import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

@MappedSuperclass
@Table(name = "product")
public abstract class AbstractProduct extends JsonAble {
    private int id;
    private String name;
    private Unit unit;

    @Override
    public JSONObject toJson(int level) {
        final JSONObject object = new JSONObject();
        object.put(ID ,id);
        object.put(NAME, name);
        return object;
    }
}
