package entity.production;

import javax.persistence.*;
import java.sql.Time;

/**
 * Created by quasilin on 01.04.2019.
 */
@Entity
@Table(name="turns")
public class Turn {
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
}
