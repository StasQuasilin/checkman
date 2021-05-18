package entity.laboratory.subdivisions.extraction;

import entity.JsonAble;
import entity.transport.ActionTime;
import org.json.simple.JSONObject;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by quasilin on 30.03.2019.
 */
@Entity
@Table(name = "extraction_crude")
public class ExtractionCrude extends JsonAble implements Comparable<ExtractionCrude>{
    private int id;
    private ExtractionTurn turn;
    private Timestamp time;
    private float humidityIncome;
    private float oilinessIncome;
    private float fraction;
    private float miscellas;
    private float humidity;
    private float dissolvent;
    private float grease;
    private float explosionTemperature;
    private float oilHumidity;
    private ActionTime createTime;
    private long massageId;

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
    @Column(name = "time")
    public Timestamp getTime() {
        return time;
    }
    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Basic
    @Column(name = "humidity_income")
    public float getHumidityIncome() {
        return humidityIncome;
    }
    public void setHumidityIncome(float humidityIncome) {
        this.humidityIncome = humidityIncome;
    }

    @Basic
    @Column(name = "oiliness_income")
    public float getOilinessIncome() {
        return oilinessIncome;
    }
    public void setOilinessIncome(float oilinessIncome) {
        this.oilinessIncome = oilinessIncome;
    }

    @Basic
    @Column(name = "fraction")
    public float getFraction() {
        return fraction;
    }
    public void setFraction(float fraction) {
        this.fraction = fraction;
    }

    @Basic
    @Column(name = "miscellas")
    public float getMiscellas() {
        return miscellas;
    }
    public void setMiscellas(float miscellas) {
        this.miscellas = miscellas;
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
    @Column(name = "dissolvent")
    public float getDissolvent() {
        return dissolvent;
    }
    public void setDissolvent(float dissolvent) {
        this.dissolvent = dissolvent;
    }

    @Basic
    @Column(name = "grease")
    public float getGrease() {
        return grease;
    }
    public void setGrease(float grease) {
        this.grease = grease;
    }

    @Basic
    @Column(name = "explosion_temperature")
    public float getExplosionTemperature() {
        return explosionTemperature;
    }
    public void setExplosionTemperature(float explosionTemperature) {
        this.explosionTemperature = explosionTemperature;
    }

    @Basic
    @Column(name = "oil_humidity")
    public float getOilHumidity() {
        return oilHumidity;
    }
    public void setOilHumidity(float oilHumidity) {
        this.oilHumidity = oilHumidity;
    }

    @OneToOne
    @JoinColumn(name = "create_time")
    public ActionTime getCreateTime() {
        return createTime;
    }
    public void setCreateTime(ActionTime createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "message_id")
    public long getMassageId() {
        return massageId;
    }
    public void setMassageId(long massageId) {
        this.massageId = massageId;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public int compareTo(ExtractionCrude o) {
        return time.compareTo(o.getTime());
    }

    @Override
    public JSONObject toJson(int level) {
        JSONObject object = pool.getObject();

        object.put(ID, id);
        object.put(TIME, time.toString());
        object.put(HUMIDITY_INCOME, humidityIncome);
        object.put(OILINESS_INCOME, oilinessIncome);
        object.put(FRACTION, fraction);
        object.put(MISCELLAS, miscellas);
        object.put(HUMIDITY, humidity);
        object.put(DISSOLVENT, dissolvent);
        object.put(GREASE, grease);
        object.put(EXPLOSION_TEMPERATURE, explosionTemperature);
        object.put(OIL_HUMIDITY, oilHumidity);

        return object;
    }
}
