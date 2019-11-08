package entity.transport;

import entity.organisations.Counterparty;

import javax.persistence.*;

/**
 * Created by szpt_user045 on 08.11.2019.
 */
@Entity
@Table(name = "trucks")
public class Truck {
    private int id;
    private String model;
    private Number number;
    private Trailer trailer;
    private Counterparty owner;
    private Counterparty transporter;

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
    public Counterparty getOwner() {
        return owner;
    }
    public void setOwner(Counterparty counterparty) {
        this.owner = counterparty;
    }

    @OneToOne
    @JoinColumn(name = "transporter")
    public Counterparty getTransporter() {
        return transporter;
    }

    public void setTransporter(Counterparty transporter) {
        this.transporter = transporter;
    }
}
