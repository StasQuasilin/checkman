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
    private boolean tehControl;


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

    @Basic
    @Column(name = "teh_control")
    public boolean isTehControl() {
        return tehControl;
    }
    public void setTehControl(boolean tehControl) {
        this.tehControl = tehControl;
    }

    @Override
    public JSONObject toJson(int level) {
        JSONObject object = pool.getObject();
        object.put(ID, id);
        object.put(NAME, name);
        object.put(TEH, tehControl);
        return object;
    }
}
