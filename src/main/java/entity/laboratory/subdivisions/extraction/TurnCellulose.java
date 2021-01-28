package entity.laboratory.subdivisions.extraction;

import entity.JsonAble;
import entity.transport.ActionTime;
import org.json.simple.JSONObject;

import javax.persistence.*;

/**
 * Created by szpt_user045 on 30.10.2019.
 */

@Entity
@Table(name = "turn_cellulose")
public class TurnCellulose extends JsonAble{
    private int id;
    private ExtractionTurn turn;
    private float cellulose;
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
    public ExtractionTurn getTurn() {
        return turn;
    }
    public void setTurn(ExtractionTurn turn) {
        this.turn = turn;
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
        if (cellulose > 0 && humidity > 0){
            return cellulose * 100 / (100 - humidity);
        }
        return 0;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = pool.getObject();
        json.put(ID, id);
        json.put(CELLULOSE, cellulose);
        json.put(HUMIDITY, humidity);
        json.put(DRY, DryRecalculation());
        return json;
    }
}
