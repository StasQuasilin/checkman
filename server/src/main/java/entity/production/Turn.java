package entity.production;

import entity.JsonAble;
import org.json.simple.JSONObject;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;

/**
 * Created by quasilin on 01.04.2019.
 */
@Entity
@Table(name = "turns")
public class Turn extends JsonAble{
    private int id;
    private Timestamp date;
    private int number;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "date")
    public Timestamp getDate() {
        return date;
    }
    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Basic
    @Column(name = "number")
    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * date.hashCode() + hash;
        hash = 31 * number + hash;
        return hash;
    }

    @Override
    public JSONObject toJson() {
        JSONObject object = pool.getObject();
        object.put(ID, id);
        object.put(DATE, date.toString());
        object.put(NUMBER, number);
        return object;
    }
}
