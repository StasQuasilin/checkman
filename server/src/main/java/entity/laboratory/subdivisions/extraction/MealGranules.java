package entity.laboratory.subdivisions.extraction;

import entity.JsonAble;
import entity.transport.ActionTime;
import org.json.simple.JSONObject;

import javax.persistence.*;

/**
 * Created by szpt_user045 on 04.01.2020.
 */
@Entity
@Table(name = "meal_granules")
public class MealGranules extends JsonAble{
    private int id;
    private ExtractionTurn turn;
    private float scree;
    private float density;
    private float humidity;
    private float length;
    private float diameter;
    private ActionTime createTime;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Column(name = "scree")
    public float getScree() {
        return scree;
    }
    public void setScree(float scree) {
        this.scree = scree;
    }

    @Basic
    @Column(name = "density")
    public float getDensity() {
        return density;
    }
    public void setDensity(float density) {
        this.density = density;
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
    @Column(name = "_length")
    public float getLength() {
        return length;
    }
    public void setLength(float length) {
        this.length = length;
    }

    @Basic
    @Column(name = "diameter")
    public float getDiameter() {
        return diameter;
    }
    public void setDiameter(float diameter) {
        this.diameter = diameter;
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
    public JSONObject toJson() {
        JSONObject object = pool.getObject();
        object.put(ID, id);
        object.put(SCREE, scree);
        object.put(DENSITY, density);
        object.put(HUMIDITY, humidity);
        object.put(LENGTH, length);
        object.put(DIAMETER, diameter);
        object.put(TIME, createTime.getTime().toString());
        return object;
    }
}
