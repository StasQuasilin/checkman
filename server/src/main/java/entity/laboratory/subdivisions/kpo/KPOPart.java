package entity.laboratory.subdivisions.kpo;

import entity.Worker;
import entity.transport.ActionTime;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by szpt_user045 on 22.05.2019.
 */
@Entity
@Table(name = "kpo_parts")
public class KPOPart {
    private int id;
    private Timestamp date;
    private int number;
    private boolean organoleptic;
    private float color;
    private float acid;
    private float peroxide;
    private boolean soap;
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
    @Column(name = "date")
    public Timestamp getDate() {
        return date;
    }
    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Basic
    @Column(name = "number")
    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
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
    public float getColor() {
        return color;
    }
    public void setColor(float color) {
        this.color = color;
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
    @Column(name = "soap")
    public boolean isSoap() {
        return soap;
    }
    public void setSoap(boolean soap) {
        this.soap = soap;
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
        hash = 31 * date.hashCode() + hash;
        hash = 31 * number + hash;
        hash = 31 * Boolean.hashCode(organoleptic) + hash;
        hash = 31 * Float.hashCode(acid) + hash;
        hash = 31 * Float.hashCode(peroxide) + hash;
        hash = 31 * Boolean.hashCode(soap) + hash;
        return hash;
    }
}
