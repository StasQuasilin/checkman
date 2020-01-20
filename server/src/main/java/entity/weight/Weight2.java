package entity.weight;

import entity.JsonAble;
import entity.transport.ActionTime;
import org.json.simple.JSONObject;

import javax.persistence.*;

/**
 * Created by szpt_user045 on 20.01.2020.
 */
@Entity
@Table(name = "_weights")
public class Weight2 extends JsonAble {
    private int id;
    private float brutto;
    private float tara;
    private ActionTime bruttoTime;
    private ActionTime taraTime;
    private String uid;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Column(name = "uid")
    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }

    @Transient
    public float getNetto(){
        return brutto > 0 && tara > 0 ? brutto - tara : 0;
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

    @Override
    public JSONObject toJson() {
        JSONObject object = pool.getObject();
        object.put(ID, id);
        object.put(BRUTTO, brutto);
        object.put(TARA, tara);
        object.put(BRUTTO_TIME, bruttoTime.toJson());
        object.put(TARA_TIME, bruttoTime.toJson());
        return object;
    }
}
