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

    @Override
    public JSONObject toJson() {
        final JSONObject jsonObject = new JSONObject();

        jsonObject.put(Keys.ID, id);
        jsonObject.put(Keys.NAME, name);

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
