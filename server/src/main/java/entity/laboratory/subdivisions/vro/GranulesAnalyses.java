package entity.laboratory.subdivisions.vro;

import entity.JsonAble;
import entity.transport.ActionTime;
import org.json.simple.JSONObject;

import javax.persistence.*;

/**
 * Created by szpt_user045 on 29.10.2019.
 */
@Entity
@Table(name="granules_analyses")
public class GranulesAnalyses extends JsonAble{
    private int id;
    private VROTurn turn;
    private float density;
    private float humidity;
    private float dust;
    private boolean match;
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
    @Column(name = "dust")
    public float getDust() {
        return dust;
    }
    public void setDust(float dust) {
        this.dust = dust;
    }

    @Basic
    @Column(name = "match")
    public boolean isMatch() {
        return match;
    }
    public void setMatch(boolean match) {
        this.match = match;
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
        JSONObject json = pool.getObject();
        json.put(ID, id);
        json.put(DENSITY, density);
        json.put(HUMIDITY, humidity);
        json.put(DUST, dust);
        json.put(MATCH, match);
        return json;
    }
}
