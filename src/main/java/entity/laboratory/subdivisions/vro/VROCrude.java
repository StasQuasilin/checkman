package entity.laboratory.subdivisions.vro;

import entity.Worker;
import entity.transport.ActionTime;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

/**
 * Created by szpt_user045 on 10.04.2019.
 */
@Entity
@Table(name = "vro_crude")
public class VROCrude {
    private int id;
    private VROTurn turn;
    private Timestamp time;
    private float humidityBefore;
    private float sorenessBefore;
    private float humidityAfter;
    private float sorenessAfter;
    private float huskiness;
    private float kernelOffset;
    private float pulpHumidity;
    private Set<ForpressCake> forpressCakes;
    private ActionTime createTime;
    private Worker creator;

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
    @Column(name = "pulp_humidity")
    public float getPulpHumidity() {
        return pulpHumidity;
    }
    public void setPulpHumidity(float pulpHumidity) {
        this.pulpHumidity = pulpHumidity;
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

    @OneToOne
    @JoinColumn(name = "creator")
    public Worker getCreator() {
        return creator;
    }
    public void setCreator(Worker creator) {
        this.creator = creator;
    }

    @Override
    public int hashCode() {
        int hash = 7;
//        private Timestamp time;
        hash = 31 * time.hashCode() + hash;
//        private float humidityBefore;
        hash = 31 * Float.hashCode(humidityBefore) + hash;
//        private float sorenessBefore;
        hash = 31 * Float.hashCode(sorenessBefore) + hash;
//        private float humidityAfter;
        hash = 31 * Float.hashCode(humidityAfter) + hash;
//        private float sorenessAfter;
        hash = 31 * Float.hashCode(sorenessAfter) + hash;
//        private float huskiness;
        hash = 31 * Float.hashCode(huskiness) + hash;
//        private float kernelOffset;
        hash = 31 * Float.hashCode(kernelOffset) + hash;
//        private float pulpHumidity;
        hash = 31 * Float.hashCode(pulpHumidity) + hash;
        for (ForpressCake cake : forpressCakes) {
            hash = 31 * cake.hashCode() + hash;
        }
//        private ActionTime createTime;
        hash = 31 * createTime.hashCode() + hash;
//        private Worker creator;
        hash = 31 * creator.hashCode() + hash;

        return hash;
    }
}
