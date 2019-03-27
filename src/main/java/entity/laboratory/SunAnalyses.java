package entity.laboratory;

import entity.Worker;
import entity.transport.ActionTime;

import javax.lang.model.element.Name;
import javax.persistence.*;

/**
 * Created by szpt_user045 on 27.03.2019.
 */
@Entity
@Table(name = "analyses_sun")
public class SunAnalyses{
    private int id;
    private float oiliness;
    private float humidity;
    private float soreness;
    private float oilImpurity;
    private float acidValue;
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

    @Basic
    @Column(name = "oiliness")
    public float getOiliness() {
        return oiliness;
    }
    public void setOiliness(float oiliness) {
        this.oiliness = oiliness;
    }

    @Basic
    @Column(name = "humidity")
    public float getHumidity() {
        return humidity;
    }
    public void setHumidity(float humidity) {
        this.humidity = humidity;
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
        hash = 31 * Float.hashCode(oiliness) + hash;
        hash = 31 * Float.hashCode(humidity) + hash;
        hash = 31 * Float.hashCode(soreness) + hash;
        hash = 31 * Float.hashCode(oilImpurity) + hash;
        hash = 31 * Float.hashCode(acidValue) + hash;
        if (createTime != null){
            hash = 31 * createTime.hashCode() + hash;
        }
        if (creator != null) {
            hash = 31 * creator.hashCode() + hash;
        }
        return hash;

    }
}
