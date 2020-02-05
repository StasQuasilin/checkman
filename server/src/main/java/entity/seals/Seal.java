package entity.seals;

import entity.JsonAble;
import entity.transport.Transportation;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;

import javax.persistence.*;

/**
 * Created by quasilin on 07.04.2019.
 */
@Entity
@Table(name = "seals")
public class Seal extends JsonAble implements Comparable<Seal>{
    private int id;
    private SealBatch batch;
    private int number;
    private String value;
    private Transportation cargo;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }

    @Basic
    @Column(name = "_value")
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
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
        object.put(VALUE, value);
        return object;
    }

    @Override
    public int compareTo(@NotNull Seal o) {
        return number - o.number;
    }

    @Override
    public String toString() {
        return value;
    }
}
