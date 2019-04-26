package entity.transport;

import entity.Worker;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@Entity
@Table(name = "action_time")
public class ActionTime {
    private int id;
    private Timestamp time;
    private Worker creator;

    @Id
    @GeneratedValue
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
        int hash = 7;
        hash = 31 * time.hashCode() + hash;
        hash = 31 * creator.hashCode() + hash;
        return hash;
    }

    @Override
    public String toString() {
        return time.toString();
    }
}
