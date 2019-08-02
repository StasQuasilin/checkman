package entity.laboratory.subdivisions.vro;

import entity.production.Forpress;

import javax.persistence.*;

/**
 * Created by szpt_user045 on 21.05.2019.
 */
@Entity
@Table(name = "forpress_cake")
public class ForpressCakeDailyDry {
    private int id;
    private OilMassFractionDry massFraction;
    private Forpress forpress;
    private float oilcake;

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
    public OilMassFractionDry getMassFraction() {
        return massFraction;
    }
    public void setMassFraction(OilMassFractionDry massFraction) {
        this.massFraction = massFraction;
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
    public float getOilcake() {
        return oilcake;
    }
    public void setOilcake(float oilcake) {
        this.oilcake = oilcake;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * forpress.getId() + hash;
        hash = 31 * Float.hashCode(oilcake) + hash;
        return hash;
    }
}
