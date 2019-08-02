package entity.laboratory.subdivisions.vro;

import entity.Worker;
import entity.transport.ActionTime;

import javax.annotation.Generated;
import javax.persistence.*;
import java.util.Set;

/**
 * Created by szpt_user045 on 20.05.2019.
 */
@Entity
@Table(name = "oil_mass_fractions_dry")
public class OilMassFractionDry {
    private int id;
    private VROTurn turn;
    private float seed;
    private float husk;
    private Set<ForpressCakeDailyDry> forpressCakes;
    private ActionTime createTime;
    private Worker creator;

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
    public VROTurn getTurn() {
        return turn;
    }
    public void setTurn(VROTurn turn) {
        this.turn = turn;
    }

    @Basic
    @Column(name = "seed")
    public float getSeed() {
        return seed;
    }
    public void setSeed(float seed) {
        this.seed = seed;
    }

    @Basic
    @Column(name = "husk")
    public float getHusk() {
        return husk;
    }
    public void setHusk(float husk) {
        this.husk = husk;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "massFraction", cascade = CascadeType.ALL)
    public Set<ForpressCakeDailyDry> getForpressCakes() {
        return forpressCakes;
    }
    public void setForpressCakes(Set<ForpressCakeDailyDry> forpressCakes) {
        this.forpressCakes = forpressCakes;
    }

    @OneToOne
    @JoinColumn(name = "create_time")
    public ActionTime getCreateTime() {
        return createTime;
    }
    public void setCreateTime(ActionTime createTime) {
        this.createTime = createTime;
    }

    @OneToOne
    @JoinColumn(name = "creator")
    public Worker getCreator() {
        return creator;
    }
    public void setCreator(Worker creator) {
        this.creator = creator;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * Float.hashCode(seed) + hash;
        hash = 31 * Float.hashCode(husk) + hash;
        if (forpressCakes != null) {
            for (ForpressCakeDailyDry fcd : forpressCakes) {
                hash = 31 * fcd.hashCode() + hash;
            }
        }
        return hash;
    }
}
