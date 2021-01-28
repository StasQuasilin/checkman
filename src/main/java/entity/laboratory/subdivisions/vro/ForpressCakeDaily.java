package entity.laboratory.subdivisions.vro;

import entity.JsonAble;
import entity.production.Forpress;
import org.json.simple.JSONObject;

import javax.persistence.*;

/**
 * Created by quasilin on 21.05.2019.
 */
@Entity
@Table(name = "forpress_cake")
public class ForpressCakeDaily extends JsonAble{
    private int id;
    private OilMassFraction oilMassFraction;
    private Forpress forpress;
    private float wet;
    private float humidity;
    private float dry;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "crude")
    public OilMassFraction getOilMassFraction() {
        return oilMassFraction;
    }
    public void setOilMassFraction(OilMassFraction oilMassFraction) {
        this.oilMassFraction = oilMassFraction;
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
    @Column(name = "wet")
    public float getWet() {
        return wet;
    }
    public void setWet(float wet) {
        this.wet = wet;
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
    @Column(name = "dry")
    public float getDry() {
        return dry;
    }
    public void setDry(float oiliness) {
        this.dry = oiliness;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * forpress.getId() + hash;
        hash = 31 * Float.hashCode(humidity) + hash;
        hash = 31 * Float.hashCode(dry) + hash;
        return hash;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = pool.getObject();
        json.put(ID, id);
        json.put(FORPRESS, forpress.getName());
        json.put(WET, wet);
        json.put(HUMIDITY, humidity);
        json.put(DRY, dry);
        return json;
    }
}
