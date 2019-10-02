package entity.transport;

import entity.organisations.Organisation;
import utils.U;

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
    private int hash;

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
        calculateHash();
    }

    @Basic
    @Column(name = "trailer")
    public String getTrailer() {
        return trailer;
    }
    public void setTrailer(String trailer) {
        this.trailer = trailer;
        calculateHash();
    }

    @Basic
    @Column(name = "hash")
    public int getHash() {
        return hash;
    }
    public void setHash(int hash) {
        this.hash = hash;
    }

    @OneToOne
    @JoinColumn(name = "transporter")
    public Organisation getTransporter() {
        return transporter;
    }
    public void setTransporter(Organisation transporter) {
        this.transporter = transporter;
    }

    public void calculateHash(){
        StringBuilder builder = new StringBuilder();
        if (U.exist(number)){
            for(Character c : number.toUpperCase().toCharArray()){
                if (Character.isLetter(c) || Character.isDigit(c)){
                    builder.append(c);
                }
            }
        }
        hash = builder.toString().hashCode();
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
        if (trailer != null){
            hash = 31 * trailer.hashCode() + hash;
        }
        return hash;
    }

    @Transient
    public String getValue() {
        return
            (model != null ? model : "") +
            (number != null ? " \'" + number + "\'" : "") +
            (trailer != null ? "\'" + trailer + "\'" : "");
    }

    @Override
    public String toString() {
        return getValue();
    }
}
