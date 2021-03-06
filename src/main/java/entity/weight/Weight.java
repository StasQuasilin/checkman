package entity.weight;

import entity.JsonAble;
import entity.transport.ActionTime;
import org.json.simple.JSONObject;

import javax.persistence.*;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@Entity
@Table(name = "weights")
public class Weight extends JsonAble{
    private int id;
    private float brutto;
    private float tara;
    private float correction;
    private ActionTime bruttoTime;
    private ActionTime taraTime;
    private String uid;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "brutto")
    public float getBrutto() {
        return brutto;
    }
    public void setBrutto(float brutto) {
        this.brutto = brutto;
    }

    @Basic
    @Column(name = "tara")
    public float getTara() {
        return tara;
    }
    public void setTara(float tara) {
        this.tara = tara;
    }

    @Basic
    @Column(name = "correction")
    public float getCorrection() {
        return correction;
    }
    public void setCorrection(float correction) {
        this.correction = correction;
    }

    @OneToOne
    @JoinColumn(name = "brutto_time")
    public ActionTime getBruttoTime() {
        return bruttoTime;
    }
    public void setBruttoTime(ActionTime bruttoTime) {
        this.bruttoTime = bruttoTime;
    }

    @OneToOne
    @JoinColumn(name = "tara_time")
    public ActionTime getTaraTime() {
        return taraTime;
    }
    public void setTaraTime(ActionTime taraTime) {
        this.taraTime = taraTime;
    }

    @Basic
    @Column(name = "uid")
    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }

    @Transient
    public float getNetto(){
        if (brutto == 0 || tara == 0){
            return 0;
        } else {
            return 1f * Math.round((brutto - tara) * 100) / 100;
        }
    }

    @Transient
    public float getCorrectedNetto(){
        return 1f * Math.round(getNetto() * (1 - correction / 100) * 1000) / 1000;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public JSONObject toJson(int level) {
        JSONObject json = pool.getObject();
        json.put(ID, id);
        json.put(BRUTTO, brutto);
        json.put(GROSS, brutto);
        json.put(TARA, tara);
        json.put(TARE, tara);
        json.put(NETTO, getNetto());
        json.put(NET, getNetto());
        json.put(CORRECTION, correction);
        json.put(CORRECTED, getCorrectedNetto());
        return json;
    }
}
