package entity.transport;

import entity.JsonAble;
import entity.Worker;
import org.json.simple.JSONObject;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@Entity
@Table(name = "action_time")
public class ActionTime extends JsonAble {
    private int id;
    private Timestamp time;
    private Worker creator;

    public ActionTime() {}
    public ActionTime(Worker worker) {
        time = new Timestamp(System.currentTimeMillis());
        this.creator = worker;
    }

    @Id
    @GeneratedValue
//            (strategy = GenerationType.SEQUENCE)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "time")
    public Timestamp getTime() {
        return time;
    }
    public void setTime(Timestamp time) {
        this.time = time;
    }

    @OneToOne
    @JoinColumn(name = "creator")
    public Worker getCreator() {
        return creator;
    }
    public void setCreator(Worker creator) {
        this.creator = creator;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return time.toString();
    }

    @Override
    public JSONObject toJson(int level) {
        JSONObject object = pool.getObject();
        object.put(ID, id);
        object.put(CREATOR, creator.toShortJson());
        object.put(TIME, time.toString());
        return object;
    }
}
