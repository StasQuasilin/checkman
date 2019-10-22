package entity.transport;

import entity.Worker;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by szpt_user045 on 24.06.2019.
 */
@Entity
@Table(name = "transportation_notes")
public class TransportationNote {
    private int id;
    private Transportation transportation;
    private Timestamp time;
    private String note;
    private Worker creator;

    public TransportationNote(Transportation transportation, Worker creator) {
        this.transportation = transportation;
        this.creator = creator;
        time = new Timestamp(System.currentTimeMillis());
    }

    public TransportationNote() {}

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "transportation")
    public Transportation getTransportation() {
        return transportation;
    }
    public void setTransportation(Transportation transportation) {
        this.transportation = transportation;
    }

    @Basic
    @Column(name = "date")
    public Timestamp getTime() {
        return time;
    }
    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Basic
    @Column(name = "note")
    public String getNote() {
        return note;
    }
    public void setNote(String note) {
        this.note = note;
    }

    @OneToOne
    @JoinColumn(name = "creator")
    public Worker getCreator() {
        return creator;
    }
    public void setCreator(Worker creator) {
        this.creator = creator;
    }
}
