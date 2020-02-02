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
    @Column(name = "acid")
    public float getAcid() {
        return acid;
    }
    public void setAcid(float acid) {
        this.acid = acid;
    }

    @Basic
    @Column(name = "peroxide")
    public float getPeroxide() {
        return peroxide;
    }
    public void setPeroxide(float peroxide) {
        this.peroxide = peroxide;
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
    @Column(name = "color")
    public float getColor() {
        return color;
    }
    public void setColor(float color) {
        this.color = color;
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
        return json;
    }
}
