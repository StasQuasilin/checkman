package entity.references;

import constants.Keys;
import org.json.simple.JSONObject;
import utils.json.JsonAble;

import javax.persistence.*;

@Entity
@Table(name = "vehicles")
public class Vehicle extends JsonAble {
    private int id;
    private String model;
    private String number;
    private Organisation transporter;
    private Organisation owner;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "_model")
    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }

    @Basic
    @Column(name = "_number")
    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }

    @OneToOne
    @JoinColumn(name = "_transporter")
    public Organisation getTransporter() {
        return transporter;
    }
    public void setTransporter(Organisation transporter) {
        this.transporter = transporter;
    }

    @OneToOne
    @JoinColumn(name = "_owner")
    public Organisation getOwner() {
        return owner;
    }
    public void setOwner(Organisation owner) {
        this.owner = owner;
    }

    @Override
    public JSONObject toJson() {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put(Keys.ID, id);
        jsonObject.put(Keys.MODEL, model);
        jsonObject.put(Keys.NUMBER, number);
        return jsonObject;
    }
}
