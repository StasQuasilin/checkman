package entity;

import org.json.simple.JSONObject;

import javax.persistence.*;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@Entity
@Table(name = "subdivisions")
public class Subdivision extends JsonAble {
    private int id;
    private String name;
    private SubdivisionKey key;


    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "key")
    public SubdivisionKey getKey() {
        return key;
    }
    public void setKey(SubdivisionKey key) {
        this.key = key;
    }

    @Override
    public JSONObject toJson() {
        JSONObject object = pool.getObject();
        object.put(ID, id);
        object.put(NAME, name);
        return object;
    }
}
