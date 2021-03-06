package entity.laboratory.subdivisions.extraction;

import entity.JsonAble;
import entity.Worker;
import entity.transport.ActionTime;
import org.json.simple.JSONObject;

import javax.persistence.*;

/**
 * Created by szpt_user045 on 04.04.2019.
 */
@Entity
@Table(name = "extraction_oil")
public class ExtractionOIl extends JsonAble{
    private int id;
    private ExtractionTurn turn;
    private float humidity;
    private float acid;
    private float peroxide;
    private float phosphorus;
    private float explosionT;
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
    public ExtractionTurn getTurn() {
        return turn;
    }
    public void setTurn(ExtractionTurn turn) {
        this.turn = turn;
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
    @Column(name = "explosion_t")
    public float getExplosionT() {
        return explosionT;
    }
    public void setExplosionT(float explosionT) {
        this.explosionT = explosionT;
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
    public JSONObject toJson(int level) {
        JSONObject json = pool.getObject();
        json.put(ID, id);
        json.put(HUMIDITY, humidity);
        json.put(ACID, acid);
        json.put(PEROXIDE, peroxide);
        json.put(PHOSPHORUS, phosphorus);
        json.put(EXPLOSION_TEMPERATURE, explosionT);
        return json;
    }
}
