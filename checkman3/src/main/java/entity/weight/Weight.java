package entity.weight;

import constants.Keys;
import org.json.simple.JSONObject;
import utils.json.JsonAble;

import javax.persistence.*;

@Entity
@Table(name = "weights")
public class Weight extends JsonAble {
    private int id;
    private float gross;
    private float tare;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "_gross")
    public float getGross() {
        return 1f * gross / 100;
    }
    public void setGross(float gross) {
        this.gross = gross;
    }

    @Basic
    @Column(name = "_tare")
    public float getTare() {
        return tare;
    }
    public void setTare(float tare) {
        this.tare = tare;
    }

    @Override
    public JSONObject toJson() {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put(Keys.ID, id);
        jsonObject.put(Keys.GROSS, gross);
        jsonObject.put(Keys.TARE, tare);
        return jsonObject;
    }
}
