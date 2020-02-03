package entity.seals;

import entity.JsonAble;
import entity.transport.Transportation;
import org.json.simple.JSONObject;

import javax.persistence.*;

/**
 * Created by quasilin on 07.04.2019.
 */
@Entity
@Table(name = "seals")
public class Seal extends JsonAble{
    private int id;
    private SealBatch batch;
    private String number;
    private Transportation cargo;

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
    public Transportation getCargo() {
        return cargo;
    }
    public void setCargo(Transportation transportation) {
        this.cargo = transportation;
    }

    @Override
    public JSONObject toJson() {
        JSONObject object = pool.getObject();
        object.put(ID, id);
        object.put(NUMBER, number);
        return object;
    }
}
