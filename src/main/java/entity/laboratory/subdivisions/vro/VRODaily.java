package entity.laboratory.subdivisions.vro;

import entity.Worker;
import entity.transport.ActionTime;

import javax.persistence.*;

/**
 * Created by szpt_user045 on 11.04.2019.
 */
@Entity
@Table(name = "vro_dailies")
public class VRODaily {
    private int id;
    private VROTurn turn;
    private float kernelHumidity;
    private float huskHumidity;
    private float huskSoreness;
    private float kernelPercent;
    private float huskPercent;
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
//        private float kernelHumidity;
        hash = 31 * Float.hashCode(kernelHumidity) + hash;
//        private float huskHumidity;
        hash = 31 * Float.hashCode(huskHumidity) + hash;
//        private float huskSoreness;
        hash = 31 * Float.hashCode(huskSoreness) + hash;
//        private float kernelPercent;
        hash = 31 * Float.hashCode(kernelPercent) + hash;
//        private float huskPercent;
        hash = 31 * Float.hashCode(huskPercent) + hash;
//        private ActionTime createTime;
        if (createTime != null) {
            hash = 31 * createTime.hashCode() + hash;
        }
//        private Worker creator;
        if (creator != null) {
            hash = 31 * creator.hashCode() + hash;
        }

        return hash;
    }
}
