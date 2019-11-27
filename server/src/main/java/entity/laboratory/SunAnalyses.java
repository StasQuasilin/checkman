package entity.laboratory;

import entity.Worker;
import entity.transport.ActionTime;

import javax.persistence.*;

/**
 * Created by szpt_user045 on 27.03.2019.
 */
@Entity
@Table(name = "analyses_sun")
public class SunAnalyses{
    private int id;
    private float oiliness;
    private float humidity1;
    private float humidity2;
    private float soreness;
    private float oilImpurity;
    private float acidValue;
    private boolean contamination;
    private ActionTime createTime;
    private int act;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "oiliness")
    public float getOiliness() {
        return oiliness;
    }
    public void setOiliness(float oiliness) {
        this.oiliness = oiliness;
    }

    @Basic
    @Column(name = "humidity1")
    public float getHumidity1() {
        return humidity1;
    }
    public void setHumidity1(float humidity) {
        this.humidity1 = humidity;
    }

    @Basic
    @Column(name = "humidity2")
    public float getHumidity2() {
        return humidity2;
    }
    public void setHumidity2(float humidity) {
        this.humidity2 = humidity;
    }

    @Basic
    @Column(name="soreness")
    public float getSoreness() {
        return soreness;
    }
    public void setSoreness(float soreness) {
        this.soreness = soreness;
    }

    @Basic
    @Column(name = "oil_impurity")
    public float getOilImpurity() {
        return oilImpurity;
    }
    public void setOilImpurity(float oilImpurity) {
        this.oilImpurity = oilImpurity;
    }

    @Basic
    @Column(name = "acid_value")
    public float getAcidValue() {
        return acidValue;
    }
    public void setAcidValue(float acidValue) {
        this.acidValue = acidValue;
    }

    @Basic
    @Column(name = "contamination")
    public boolean isContamination() {
        return contamination;
    }
    public void setContamination(boolean contamination) {
        this.contamination = contamination;
    }

    @OneToOne
    @JoinColumn(name = "create_time")
    public ActionTime getCreateTime() {
        return createTime;
    }
    public void setCreateTime(ActionTime createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "act")
    public int getAct() {
        return act;
    }
    public void setAct(int act) {
        this.act = act;
    }

    @Transient
    public float middleHumidity(){
        return humidity1 > 0 && humidity2 > 0 ? (humidity1 + humidity2) / 2 : humidity1 > 0 ? humidity1 : humidity2 > 0 ? humidity2 : 0;
    }

    @Override
    public int hashCode() {
        return id;

    }
}
