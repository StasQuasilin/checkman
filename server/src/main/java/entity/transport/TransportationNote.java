package entity.transport;

import entity.JsonAble;
import entity.Worker;
import org.json.simple.JSONObject;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by szpt_user045 on 24.06.2019.
 */
@Entity
@Table(name = "_transportation_notes")
public class TransportationNote extends JsonAble {
    private int id;
    private Transportation2 transportation;
    private Timestamp time;
    private String note;
    private Worker creator;

    public TransportationNote(Transportation2 transportation, Worker creator) {
        this.transportation = transportation;
        this.creator = creator;
        time = new Timestamp(System.currentTimeMillis());
    }

    public TransportationNote() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "transportation")
    public Transportation2 getTransportation() {
        return transportation;
    }
    public void setTransportation(Transportation2 transportation) {
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

    @Override
    public JSONObject toJson() {
        JSONObject json = pool.getObject();
        json.put(ID, id);
        json.put(NOTE, note);
        if (creator != null) {
            json.put(CREATOR, creator.toJson());
        }
        return json;
    }
}
