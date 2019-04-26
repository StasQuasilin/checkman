package entity.transport;

import entity.organisations.Organisation;

import javax.persistence.*;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@Entity
@Table(name = "vehicles")
public class Vehicle {
    private int id;
    private String model;
    private String number;
    private String trailer;
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

    @Basic
    @Column(name = "trailer")
    public String getTrailer() {
        return trailer;
    }
    public void setTrailer(String trailer) {
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
        int hash = 7;
        hash = 31 * model.hashCode() + hash;
        hash = 31 * number.hashCode() + hash;
        if (trailer != null){
            hash = 31 * trailer.hashCode() + hash;
        }
        return hash;
    }

    @Transient
    public String getValue() {
        return model + " \'" + number + "\'" + (trailer != number ? "\'" + trailer + "\'" : "");
    }

    @Override
    public String toString() {
        return getValue();
    }
}
