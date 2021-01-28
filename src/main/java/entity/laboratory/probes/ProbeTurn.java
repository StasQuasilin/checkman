package entity.laboratory.probes;

import entity.JsonAble;
import entity.production.Turn;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by szpt_user045 on 19.07.2019.
 */
@Entity
@Table(name = "turns_probes")
public class ProbeTurn extends JsonAble{
    private int id;
    private Turn turn;
    private Set<SunProbe> sunProbes = new HashSet<>();
    private Set<OilProbe> oilProbes = new HashSet<>();
    private Set<MealProbe> mealProbes = new HashSet<>();
    private Set<CakeProbe> cakeProbes = new HashSet<>();

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @OneToOne
    @JoinColumn(name = "turn")
    public Turn getTurn() {
        return turn;
    }
    public void setTurn(Turn turn) {
        this.turn = turn;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "turn", cascade = CascadeType.ALL)
    public Set<SunProbe> getSunProbes() {
        return sunProbes;
    }
    public void setSunProbes(Set<SunProbe> sunProbes) {
        this.sunProbes = sunProbes;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "turn", cascade = CascadeType.ALL)
    public Set<OilProbe> getOilProbes() {
        return oilProbes;
    }
    public void setOilProbes(Set<OilProbe> oilProbes) {
        this.oilProbes = oilProbes;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "turn", cascade = CascadeType.ALL)
    public Set<MealProbe> getMealProbes() {
        return mealProbes;
    }
    public void setMealProbes(Set<MealProbe> mealProbes) {
        this.mealProbes = mealProbes;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "turn", cascade = CascadeType.ALL)
    public Set<CakeProbe> getCakeProbes() {
        return cakeProbes;
    }
    public void setCakeProbes(Set<CakeProbe> cakeProbes) {
        this.cakeProbes = cakeProbes;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        return getClass() == obj.getClass() && hashCode() == obj.hashCode();
    }

    @Override
    public JSONObject toJson() {
        JSONObject object = turn.toJson();
        object.put(SUN, sun());
        object.put(OIL, oil());
        return object;
    }

    private JSONArray oil() {
        JSONArray array = pool.getArray();
        array.addAll(oilProbes.stream().map(OilProbe::toJson).collect(Collectors.toList()));
        return array;
    }

    private JSONArray sun() {
        JSONArray array = pool.getArray();
        array.addAll(sunProbes.stream().map(SunProbe::toJson).collect(Collectors.toList()));
        return array;
    }
}
