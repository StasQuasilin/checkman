package entity.references;

import constants.Keys;
import org.json.simple.JSONObject;
import utils.json.JsonAble;

import javax.persistence.*;

@Entity
@Table(name = "organisations")
public class Organisation extends JsonAble {
    private int id;
    private String name;
    String type;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "_name")
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "_type")
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public JSONObject toJson() {
        final JSONObject jsonObject = new JSONObject();

        jsonObject.put(Keys.ID, id);
        jsonObject.put(Keys.NAME, name);
        jsonObject.put(Keys.TYPE, type);

        return jsonObject;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        return obj.getClass().equals(getClass()) && hashCode() == obj.hashCode();
    }
}
