package entity.laboratory.subdivisions.vro;

import entity.JsonAble;
import entity.Worker;
import entity.transport.ActionTime;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by szpt_user045 on 20.05.2019.
 */
@Entity
@Table(name = "oil_mass_fractions")
public class OilMassFraction extends JsonAble{
    private int id;
    private VROTurn turn;
    private float seedWet;
    private float seedHumidity;
    private float seedDry;
    private float huskWet;
    private float huskHumidity;
    private float huskDry;
    private Set<ForpressCakeDaily> forpressCakes;
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
    @Column(name = "seed_wet")
    public float getSeedWet() {
        return seedWet;
    }
    public void setSeedWet(float seedWed) {
        this.seedWet = seedWed;
    }

    @Basic
    @Column(name = "seed_humidity")
    public float getSeedHumidity() {
        return seedHumidity;
    }
    public void setSeedHumidity(float seedHumidity) {
        this.seedHumidity = seedHumidity;
    }

    @Basic
    @Column(name = "seed_dry")
    public float getSeedDry() {
        return seedDry;
    }
    public void setSeedDry(float seedDry) {
        this.seedDry = seedDry;
    }

    @Basic
    @Column(name = "husk_wet")
    public float getHuskWet() {
        return huskWet;
    }
    public void setHuskWet(float husk) {
        this.huskWet = husk;
    }

    @Basic
    @Column(name = "husk_humidity")
    public float getHuskHumidity() {
        return huskHumidity;
    }
    public void setHuskHumidity(float huskHumidity) {
        this.huskHumidity = huskHumidity;
    }

    @Basic
    @Column(name = "husk_dry")
    public float getHuskDry() {
        return huskDry;
    }
    public void setHuskDry(float huskDry) {
        this.huskDry = huskDry;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "oilMassFraction", cascade = CascadeType.ALL)
    public Set<ForpressCakeDaily> getForpressCakes() {
        return forpressCakes;
    }
    public void setForpressCakes(Set<ForpressCakeDaily> forpressCakes) {
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
        return id;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = pool.getObject();
        json.put(ID, id);
        json.put(SEED_WET, seedWet);
        json.put(SEED_HUMIDITY, seedHumidity);
        json.put(SEED_DRY, seedDry);
        json.put(HUSK_WET, huskWet);
        json.put(HUSK_HUMIDITY, huskHumidity);
        json.put(HUSK_DRY, huskDry);
        json.put(FORPRESS, forpressCake());
        return json;
    }

    private JSONArray forpressCake() {
        JSONArray array = pool.getArray();
        for (ForpressCakeDaily cake : forpressCakes){
            array.add(cake.toJson());
        }
        return array;
    }
}
