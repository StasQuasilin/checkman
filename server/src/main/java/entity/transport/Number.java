package entity.transport;

import entity.JsonAble;
import org.json.simple.JSONObject;

import javax.persistence.*;

/**
 * Created by szpt_user045 on 08.11.2019.
 */
@Entity
@Table(name = "numbers")
public class Number extends JsonAble{
    private int id;
    private String number;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "_number")
    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json =pool.getObject();
        json.put(ID, id);
        json.put(NUMBER, number);
        return json;
    }
}
