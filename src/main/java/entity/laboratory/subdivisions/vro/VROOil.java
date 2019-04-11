package entity.laboratory.subdivisions.vro;

import entity.Worker;
import entity.transport.ActionTime;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by szpt_user045 on 10.04.2019.
 */
@Entity
@Table(name = "vro_oil")
public class VROOil {
    private int id;
    private VROTurn turn;
    private float acid;
    private float peroxide;
    private float phosphorus;
    private int color;
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
    public int getColor() {
        return color;
    }
    public void setColor(int color) {
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
        int hash = 7;
//        private float acid;
        hash = 31 * Float.hashCode(acid) + hash;
//        private float peroxide;
        hash = 31 * Float.hashCode(peroxide) + hash;
//        private float phosphorus;
        hash = 31 * Float.hashCode(phosphorus) + hash;
//        private int color;
        hash = 31 * color + hash;
//        private ActionTime createTime;
        hash = 31 * createTime.hashCode() + hash;
//        private Worker creator;
        hash = 31 * creator.hashCode() + hash;

        return hash;
    }
}
