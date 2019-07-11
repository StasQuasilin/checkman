package entity.seals;

import entity.transport.Transportation;

import javax.persistence.*;

/**
 * Created by quasilin on 07.04.2019.
 */
@Entity
@Table(name = "seals")
public class Seal {
    private int id;
    private SealBatch batch;
    private String number;
    private Transportation transportation;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @OneToOne
    @JoinColumn(name = "batch")
    public SealBatch getBatch() {
        return batch;
    }
    public void setBatch(SealBatch batch) {
        this.batch = batch;
    }

    @Basic
    @Column(name = "number")
    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }

    @ManyToOne
    @JoinColumn(name = "cargo")
    public Transportation getTransportation() {
        return transportation;
    }
    public void setTransportation(Transportation transportation) {
        this.transportation = transportation;
    }
}
