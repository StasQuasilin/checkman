package entity.laboratory.subdivisions.vro;

import entity.JsonAble;
import entity.Worker;
import entity.transport.ActionTime;
import org.json.simple.JSONObject;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by szpt_user045 on 10.04.2019.
 */
@Entity
@Table(name = "vro_oil")
public class VROOil extends JsonAble{
    private int id;
    private VROTurn turn;
    private float acid;
    private float peroxide;
    private float phosphorus;
    private float color;
    private float impurity;
    private float humidity;
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
    @JoinColumn(name = TURN)
    public VROTurn getTurn() {
        return turn;
    }
    public void setTurn(VROTurn turn) {
        this.turn = turn;
    }

    @Basic
    @Column(name = ACID)
    public float getAcid() {
        return acid;
    }
    public void setAcid(float acid) {
        this.acid = acid;
    }

    @Basic
    @Column(name = PEROXIDE)
    public float getPeroxide() {
        return peroxide;
    }
    public void setPeroxide(float peroxide) {
        this.peroxide = peroxide;
    }

    @Basic
    @Column(name = PHOSPHORUS)
    public float getPhosphorus() {
        return phosphorus;
    }
    public void setPhosphorus(float phosphorus) {
        this.phosphorus = phosphorus;
    }

    @Basic
    @Column(name = COLOR)
    public float getColor() {
        return color;
    }
    public void setColor(float color) {
        this.color = color;
    }

    @Basic
    @Column(name = IMPURITY)
    public float getImpurity() {
        return impurity;
    }
    public void setImpurity(float impurity) {
        this.impurity = impurity;
    }

    @Basic
    @Column(name = HUMIDITY)
    public float getHumidity() {
        return humidity;
    }
    public void setHumidity(float humidity) {
        this.humidity = humidity;
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
        json.put(ACID, acid);
        json.put(PEROXIDE, peroxide);
        json.put(PHOSPHORUS, phosphorus);
        json.put(COLOR, color);
        json.put(IMPURITY, impurity);
        json.put(HUMIDITY, humidity);
        return json;
    }
}
