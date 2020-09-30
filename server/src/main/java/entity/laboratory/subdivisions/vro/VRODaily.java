package entity.laboratory.subdivisions.vro;

import entity.JsonAble;
import entity.Worker;
import entity.transport.ActionTime;
import org.json.simple.JSONObject;

import javax.persistence.*;

/**
 * Created by szpt_user045 on 11.04.2019.
 */
@Entity
@Table(name = "vro_dailies")
public class VRODaily extends JsonAble {
    private int id;
    private VROTurn turn;
    private float dryOiliness;
    private float kernelHumidity;
    private float huskHumidity;
    private float huskSoreness;
    private float kernelPercent;
    private float huskPercent;
    private ActionTime createTime;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "turn")
    public VROTurn getTurn() {
        return turn;
    }
    public void setTurn(VROTurn turn) {
        this.turn = turn;
    }

    @Basic
    @Column(name = "dry_oiliness")
    public float getDryOiliness() {
        return dryOiliness;
    }
    public void setDryOiliness(float dryOiliness) {
        this.dryOiliness = dryOiliness;
    }

    @Basic
    @Column(name = "kernel_humidity")
    public float getKernelHumidity() {
        return kernelHumidity;
    }
    public void setKernelHumidity(float kernelHumidity) {
        this.kernelHumidity = kernelHumidity;
    }

    @Basic
    @Column(name = "husk_humidity")
    public float getHuskHumidity() {
        return huskHumidity;
    }
    public void setHuskHumidity(float huskHumidity) {
        this.huskHumidity = huskHumidity;
    }

    @Basic
    @Column(name = "husk_soreness")
    public float getHuskSoreness() {
        return huskSoreness;
    }
    public void setHuskSoreness(float huskSoreness) {
        this.huskSoreness = huskSoreness;
    }

    @Basic
    @Column(name = "kernel_percent")
    public float getKernelPercent() {
        return kernelPercent;
    }
    public void setKernelPercent(float kernelPercent) {
        this.kernelPercent = kernelPercent;
    }

    @Basic
    @Column(name = "husk_percent")
    public float getHuskPercent() {
        return huskPercent;
    }
    public void setHuskPercent(float huskPercent) {
        this.huskPercent = huskPercent;
    }

    @OneToOne
    @JoinColumn(name = "create_time")
    public ActionTime getCreateTime() {
        return createTime;
    }
    public void setCreateTime(ActionTime createTime) {
        this.createTime = createTime;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = pool.getObject();
        json.put(ID, id);
        json.put(DRY, dryOiliness);
        json.put(KERNEL_HUMIDITY, kernelHumidity);
        json.put(HUSK_HUMIDITY, huskHumidity);
        json.put(HUSK_SORENESS, huskSoreness);
        json.put(KERNEL_PERCENT, kernelPercent);
        json.put(HUSK_PERCENT, huskPercent);
        return json;
    }
}
