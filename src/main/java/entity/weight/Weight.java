package entity.weight;

import entity.transport.ActionTime;
import entity.transport.Transportation;

import javax.persistence.*;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@Entity
@Table(name = "weights")
public class Weight {
    private int id;
    private Transportation transportation;
    private float brutto;
    private float tara;
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

    @ManyToOne
    @JoinColumn(name = "transportation", nullable = false)
    public Transportation getTransportation() {
        return transportation;
    }
    public void setTransportation(Transportation transportation) {
        this.transportation = transportation;
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
            return brutto - tara;
        }
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
}
