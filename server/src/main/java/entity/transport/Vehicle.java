package entity.transport;

import entity.JsonAble;
import entity.organisations.Organisation;
import org.json.simple.JSONObject;

import javax.persistence.*;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@Entity
@Table(name = "vehicles")
public class Vehicle extends JsonAble {
    private int id;
    private String model;
    private String number;
    private Trailer trailer;
    private String trailerNumber;
    private Organisation transporter;

    @Id
    @GeneratedValue
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

    @Basic
    @Column(name = "number")
    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
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

    @Basic
    @Column(name = "trailer_number")
    public String getTrailerNumber() {
        return trailerNumber;
    }
    public void setTrailerNumber(String trailer) {
        this.trailerNumber = trailer;
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
    public int hashCode() {
        int hash = 7;
        if (model != null) {
            hash = 31 * model.hashCode() + hash;
        }
        if (number != null) {
            hash = 31 * number.hashCode() + hash;
        }
        if (trailerNumber != null){
            hash = 31 * trailerNumber.hashCode() + hash;
        }
        return hash;
    }

    @Transient
    public String getValue() {
        return
            (model != null ? model : "") +
            (number != null ? " \'" + number + "\'" : "") +
            (trailerNumber != null ? "\'" + trailerNumber + "\'" : "");
    }

    @Override
    public String toString() {
        return getValue();
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = pool.getObject();
        json.put(ID, id);
        json.put(MODEL, model);
        json.put(NUMBER, number);
        if (trailer != null) {
            json.put(TRAILER, trailer.toJson());
        }
        return json;
    }
}
