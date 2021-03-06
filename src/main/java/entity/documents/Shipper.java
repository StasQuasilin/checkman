package entity.documents;

import entity.JsonAble;
import org.json.simple.JSONObject;

import javax.persistence.*;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@Entity
@Table(name = "document_organisations")
public class Shipper extends JsonAble{
    private int id;
    private String value;
    private boolean active;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "value")
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }

    @Basic
    @Column(name = "active")
    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        return getClass() == obj.getClass() && hashCode() == obj.hashCode();
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public JSONObject toJson(int level) {
        JSONObject object = pool.getObject();
        object.put(ID, id);
        object.put(NAME, value);
        return object;
    }
}
