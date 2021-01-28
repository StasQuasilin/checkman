package entity.laboratory;

import entity.Worker;
import entity.transport.ActionTime;

import javax.persistence.*;

/**
 * Created by szpt_user045 on 27.03.2019.
 */
@Entity
@Table(name = "analyses_meal")
public class CakeAnalyses {
    private int id;
    private float humidity;
    private float protein;
    private float cellulose;
    private float oiliness;
    private ActionTime createTime;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
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
    @Column(name = "protein")
    public float getProtein() {
        return protein;
    }
    public void setProtein(float protein) {
        this.protein = protein;
    }

    @Basic
    @Column(name = "cellulose")
    public float getCellulose() {
        return cellulose;
    }
    public void setCellulose(float cellulose) {
        this.cellulose = cellulose;
    }

    @Basic
    @Column(name = "oiliness")
    public float getOiliness() {
        return oiliness;
    }
    public void setOiliness(float oiliness) {
        this.oiliness = oiliness;
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
}
