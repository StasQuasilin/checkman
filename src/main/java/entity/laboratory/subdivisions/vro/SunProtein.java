package entity.laboratory.subdivisions.vro;

import entity.JsonAble;
import entity.transport.ActionTime;
import org.json.simple.JSONObject;

import javax.persistence.*;

/**
 * Created by szpt_user045 on 30.10.2019.
 */
@Entity
@Table(name = "sun_protein")
public class SunProtein extends JsonAble{
    private int id;
    private VROTurn turn;
    private float protein;
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
    @JoinColumn(name = "turn")
    public VROTurn getTurn() {
        return turn;
    }
    public void setTurn(VROTurn turn) {
        this.turn = turn;
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
    @Column(name = "humidity")
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

    public float DryRecalculation() {
        if (protein > 0 && humidity > 0){
            return protein * 100 / (100 - humidity);
        }
        return 0;
    }

    @Override
    public JSONObject toJson(int level) {

        JSONObject json = pool.getObject();
        json.put(ID, id);
        json.put(PROTEIN, protein);
        json.put(HUMIDITY, humidity);
        json.put(DRY, DryRecalculation());
        return json;
    }
}
