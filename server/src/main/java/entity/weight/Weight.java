package entity.weight;

import entity.JsonAble;
import entity.transport.ActionTime;
import entity.transport.Transportation;
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
            return (brutto - tara);
        }
    }

    @Transient
    public float getCorrectedNetto(){
        return getNetto() * (1 - correction / 100);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * Float.hashCode(brutto) + hash;
        hash = 31 & Float.hashCode(tara) + hash;
        if (bruttoTime != null){
            hash = 31 * bruttoTime.hashCode() + hash;
        }
        if (taraTime != null) {
            hash = 31 * taraTime.hashCode() + hash;
        }

        return hash;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = pool.getObject();
        json.put(ID, id);
        json.put(BRUTTO , brutto);
        json.put(TARA, tara);
        json.put(NETTO, getNetto());
        return json;
    }
}
