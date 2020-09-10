package entity.transportations;

import constants.Keys;
import entity.ActionTime;
import org.json.simple.JSONObject;
import utils.json.JsonAble;

import javax.persistence.*;

@Entity
@Table(name = "transportation_notes")
public class TransportationNote extends JsonAble {
    private int id;
    private Transportation transportation;
    private String note;
    private ActionTime create;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @OneToOne
    @JoinColumn(name = "_transportation")
    public Transportation getTransportation() {
        return transportation;
    }
    public void setTransportation(Transportation transportation) {
        this.transportation = transportation;
    }

    @Basic
    @Column(name = "_note")
    public String getNote() {
        return note;
    }
    public void setNote(String note) {
        this.note = note;
    }

    @OneToOne
    @JoinColumn(name = "_create")
    public ActionTime getCreate() {
        return create;
    }
    public void setCreate(ActionTime create) {
        this.create = create;
    }

    @Override
    public JSONObject toJson() {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put(Keys.NOTE, note);
        jsonObject.put(Keys.CREATED, create.toJson());
        return jsonObject;
    }
}
