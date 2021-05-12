package entity.transport;

import constants.Constants;
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
    private static final String SPACE = Constants.SPACE;
    private int id;
    private String model;
    private String number;
    private Trailer trailer;
    private Organisation transporter;
    private VehicleType type;

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
        return id;
    }

    @Transient
    public String getValue() {
        return (model != null ? model : EMPTY) +
            (number != null ? " '" + number + "'" : EMPTY) + (trailer != null ? trailer.getNumber() : EMPTY);
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
        String value = model + SPACE + number;
        if (trailer != null) {
            json.put(TRAILER, trailer.toJson());
            value += SPACE + trailer.getNumber();
        }
        json.put(VALUE, value);
        return json;
    }
}
