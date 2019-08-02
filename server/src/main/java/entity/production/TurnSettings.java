package entity.production;

import javax.persistence.*;
import java.sql.Time;

/**
 * Created by szpt_user045 on 03.06.2019.
 */
@Entity
@Table(name= "turn_settings")
public class TurnSettings {
    private int id;
    private int number;
    private Time begin;
    private Time end;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "number")
    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }

    @Basic
    @Column(name = "_begin")
    public Time getBegin() {
        return begin;
    }
    public void setBegin(Time begin) {
        this.begin = begin;
    }

    @Basic
    @Column(name = "_end")
    public Time getEnd() {
        return end;
    }
    public void setEnd(Time end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "Turn{" +
                "number=" + number +
                ", begin=" + begin +
                ", end=" + end +
                '}';
    }
}
