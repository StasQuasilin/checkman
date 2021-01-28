package entity.laboratory.subdivisions.vro;

import entity.JsonAble;
import entity.Worker;
import entity.transport.ActionTime;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

/**
 * Created by szpt_user045 on 10.04.2019.
 */
@Entity
@Table(name = "vro_crude")
public class VROCrude extends JsonAble implements Comparable<VROCrude>{
    private int id;
    private VROTurn turn;
    private Timestamp time;
    private float dryOiliness;
    private float humidityBefore;
    private float sorenessBefore;
    private float humidityAfter;
    private float sorenessAfter;
    private float huskiness;
    private float kernelOffset;
    private float pulpHumidity1;
    private float pulpHumidity2;
    private Set<ForpressCake> forpressCakes;
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
    @Column(name = "time")
    public Timestamp getTime() {
        return time;
    }
    public void setTime(Timestamp time) {
        this.time = time;
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
    @Column(name = "humidity_before")
    public float getHumidityBefore() {
        return humidityBefore;
    }
    public void setHumidityBefore(float humidityBefore) {
        this.humidityBefore = humidityBefore;
    }

    @Basic
    @Column(name = "soreness_before")
    public float getSorenessBefore() {
        return sorenessBefore;
    }
    public void setSorenessBefore(float sorenessBefore) {
        this.sorenessBefore = sorenessBefore;
    }

    @Basic
    @Column(name = "humidity_after")
    public float getHumidityAfter() {
        return humidityAfter;
    }
    public void setHumidityAfter(float humidityAfter) {
        this.humidityAfter = humidityAfter;
    }

    @Basic
    @Column(name = "soreness_after")
    public float getSorenessAfter() {
        return sorenessAfter;
    }
    public void setSorenessAfter(float sorenessAfter) {
        this.sorenessAfter = sorenessAfter;
    }

    @Basic
    @Column(name = "huskiness")
    public float getHuskiness() {
        return huskiness;
    }
    public void setHuskiness(float huskiness) {
        this.huskiness = huskiness;
    }

    @Basic
    @Column(name = "kernel_offset")
    public float getKernelOffset() {
        return kernelOffset;
    }
    public void setKernelOffset(float kernelOffset) {
        this.kernelOffset = kernelOffset;
    }

    @Basic
    @Column(name = "pulp_humidity_1")
    public float getPulpHumidity1() {
        return pulpHumidity1;
    }
    public void setPulpHumidity1(float pulpHumidity) {
        this.pulpHumidity1 = pulpHumidity;
    }
    
    @Basic
    @Column(name = "pulp_humidity_2")
    public float getPulpHumidity2() {
        return pulpHumidity2;
    }
    public void setPulpHumidity2(float pulpHumidity) {
        this.pulpHumidity2 = pulpHumidity;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "crude", cascade = CascadeType.ALL)
    public Set<ForpressCake> getForpressCakes() {
        return forpressCakes;
    }
    public void setForpressCakes(Set<ForpressCake> forpressCakes) {
        this.forpressCakes = forpressCakes;
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
    public int compareTo(@NotNull VROCrude o) {
        return time.compareTo(o.time);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = pool.getObject();
        json.put(ID, id);
        json.put(TIME, time.toString());
        json.put(DRY, dryOiliness);
        json.put(HUMIDITY_BEFORE, humidityBefore);
        json.put(SORENESS_BEFORE, sorenessBefore);
        json.put(HUMIDITY_AFTER, humidityAfter);
        json.put(SORENESS_AFTER, sorenessAfter);
        json.put(HUSKINESS, huskiness);
        json.put(KERNEL_OFFSET, kernelOffset);
        json.put(PULP_HUMIDITY1, pulpHumidity1);
        json.put(PULP_HUMIDITY2, pulpHumidity2);

        json.put(CAKES, cakes());
        return json;
    }

    private JSONObject cakes() {
        JSONObject json = pool.getObject();
        for (ForpressCake cake : forpressCakes){
            json.put(cake.getForpress().getName(), cake.toJson());
        }
        return json;
    }
}
