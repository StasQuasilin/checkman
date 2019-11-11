package entity.transport;

import entity.JsonAble;
import entity.organisations.Organisation;
import org.json.simple.JSONObject;
import utils.U;

import javax.persistence.*;

/**
 * Created by szpt_user045 on 08.11.2019.
 */
@Entity
@Table(name = "trucks")
public class Truck extends JsonAble{
    private int id;
    private String model;
    private Number number;
    private Trailer trailer;
    private Organisation owner;
    private Organisation transporter;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "model")
    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }

    @OneToOne
    @JoinColumn(name = "_number")
    public Number getNumber() {
        return number;
    }
    public void setNumber(Number number) {
        this.number = number;
    }

    @OneToOne
    @JoinColumn(name = "trailer")
    public Trailer getTrailer() {
        return trailer;
    }
    public void setTrailer(Trailer trailer) {
        this.trailer = trailer;
    }

    @OneToOne
    @JoinColumn(name = "owner")
    public Organisation getOwner() {
        return owner;
    }
    public void setOwner(Organisation counterparty) {
        this.owner = counterparty;
    }

    @OneToOne
    @JoinColumn(name = "transporter")
    public Organisation getTransporter() {
        return transporter;
    }
    public void setTransporter(Organisation transporter) {
        this.transporter = transporter;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = pool.getObject();
        json.put(ID, id);
        json.put(MODEL, model);
        json.put(NUMBER, number.toJson());
        json.put(TRAILER, trailer.toJson());
        json.put(VALUE, getValue());
        if (owner != null) {
            json.put(OWNER, owner.toJson());
        }
        if (transporter != null){
            json.put(TRANSPORTER, transporter.toJson());
        }
        return json;
    }

    @Transient
    private String getValue() {
        return model;
    }
}
