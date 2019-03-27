package entity.laboratory;

import entity.Worker;
import entity.transport.ActionTime;

import javax.persistence.*;

/**
 * Created by szpt_user045 on 27.03.2019.
 */
@Entity
@Table(name = "analyses_oil")
public class OilAnalyses{
    private int id;
    private boolean organoleptic;
    private int color;
    private float acidValue;
    private float peroxideValue;
    private float phosphorus;
    private float humidity;
    private float soap;
    private float wax;
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
    @Column(name = "organoleptic")
    public boolean isOrganoleptic() {
        return organoleptic;
    }
    public void setOrganoleptic(boolean organoleptic) {
        this.organoleptic = organoleptic;
    }

    @Basic
    @Column(name = "color")
    public int getColor() {
        return color;
    }
    public void setColor(int color) {
        this.color = color;
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
    @Column(name = "peroxide")
    public float getPeroxideValue() {
        return peroxideValue;
    }
    public void setPeroxideValue(float peroxideValue) {
        this.peroxideValue = peroxideValue;
    }

    @Basic
    @Column(name = "phosphorus")
    public float getPhosphorus() {
        return phosphorus;
    }
    public void setPhosphorus(float phosphorus) {
        this.phosphorus = phosphorus;
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
    @Column(name = "soap")
    public float getSoap() {
        return soap;
    }
    public void setSoap(float soap) {
        this.soap = soap;
    }

    @Basic
    @Column(name = "wax")
    public float getWax() {
        return wax;
    }
    public void setWax(float wax) {
        this.wax = wax;
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
//        private boolean organoleptic;
        hash = 31 * Boolean.hashCode(organoleptic) + hash;
//        private int color;
        hash = 31 * Integer.hashCode(color) + hash;
//        private float acidValue;
        hash = 31 * Float.hashCode(acidValue) + hash;
//        private float peroxideValue;
        hash = 31 * Float.hashCode(peroxideValue) + hash;
//        private float phosphorus;
        hash = 31 * Float.hashCode(phosphorus) + hash;
//        private float humidity;
        hash = 31 * Float.hashCode(humidity) + hash;
//        private float soap;
        hash = 31 * Float.hashCode(soap) + hash;
//        private float wax;
        hash = 31 * Float.hashCode(wax) + hash;
//        private ActionTime createTime;
        if (createTime != null){
            hash = 31 * createTime.hashCode() + hash;
        }
//        private Worker creator;
        if (creator != null) {
            hash = 31 * creator.hashCode() + hash;
        }
        return hash;
    }
}
