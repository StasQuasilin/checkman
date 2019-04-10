package entity.laboratory.subdivisions.vro;

import entity.Worker;
import entity.production.Forpress;
import entity.transport.ActionTime;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by szpt_user045 on 10.04.2019.
 */
@Entity
@Table(name = "forpress_cake")
public class ForpressCake {
    private int id;
    private VROTurn turn;
    private Timestamp time;
    private Forpress forpress;
    private float humidity;
    private float oiliness;
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

    @OneToOne
    @JoinColumn(name = "forpress")
    public Forpress getForpress() {
        return forpress;
    }
    public void setForpress(Forpress forpress) {
        this.forpress = forpress;
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
//        private Forpress forpress;
        hash = 31 * forpress.hashCode() + hash;
//        private float humidity;
        hash = 31 * Float.hashCode(humidity) + hash;
//        private float oiliness;
        hash = 31 * Float.hashCode(oiliness) + hash;
//        private ActionTime createTime;
        hash = 31 * createTime.hashCode() + hash;
//        private Worker creator;
        hash = 31 * creator.hashCode() + hash;
        return hash;
    }
}
