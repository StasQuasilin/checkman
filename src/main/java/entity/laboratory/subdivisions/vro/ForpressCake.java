package entity.laboratory.subdivisions.vro;

import entity.JsonAble;
import entity.production.Forpress;
import org.json.simple.JSONObject;

import javax.persistence.*;

/**
 * Created by szpt_user045 on 10.04.2019.
 */
@Entity
@Table(name = "forpress_cake")
public class ForpressCake extends JsonAble{
    private int id;
    private VROCrude crude;
    private Forpress forpress;
    private float humidity;
    private float oiliness;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "wet")
    public VROCrude getCrude() {
        return crude;
    }
    public void setCrude(VROCrude turn) {
        this.crude = turn;
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
    @Column(name = "dry")
    public float getOiliness() {
        return oiliness;
    }
    public void setOiliness(float oiliness) {
        this.oiliness = oiliness;
    }

    @Override
    public int hashCode() {
        int hash = 7;
//        private Forpress forpress;
        hash = 31 * forpress.hashCode() + hash;
//        private float humidity;
        hash = 31 * Float.hashCode(humidity) + hash;
//        private float oiliness;
        hash = 31 * Float.hashCode(oiliness) + hash;

        return hash;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = pool.getObject();
        json.put(ID, id);
        json.put(FORPRESS, forpress.getName());
        json.put(HUMIDITY, humidity);
        json.put(OILINESS, oiliness);
        return json;
    }
}
