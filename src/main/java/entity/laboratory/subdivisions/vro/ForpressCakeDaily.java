package entity.laboratory.subdivisions.vro;

import entity.production.Forpress;

import javax.persistence.*;

/**
 * Created by quasilin on 21.05.2019.
 */
@Entity
@Table(name = "forpress_cake")
public class ForpressCakeDaily {
    private int id;
    private OilMassFraction oilMassFraction;
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
    @Column(name = "humidity")
    public float getHumidity() {
        return humidity;
    }
    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    @Basic
    @Column(name = "oiliness")
    public float getOiliness() {
        return oiliness;
    }
    public void setOiliness(float oiliness) {
        this.oiliness = oiliness;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * forpress.getId() + hash;
        hash = 31 * Float.hashCode(humidity) + hash;
        hash = 31 * Float.hashCode(oiliness) + hash;
        return hash;
    }
}
