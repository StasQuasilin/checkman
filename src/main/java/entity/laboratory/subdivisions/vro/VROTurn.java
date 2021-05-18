package entity.laboratory.subdivisions.vro;

import entity.JsonAble;
import entity.production.Turn;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by szpt_user045 on 10.04.2019.
 */
@Entity
@Table(name = "turns_vro")
public class VROTurn extends JsonAble{
    private int id;
    private Turn turn;
    private List<VROCrude> crudes = new ArrayList<>();
    private Set<VROOil> oils;
    private Set<VRODaily> dailies;
    private Set<OilMassFraction> oilMassFractions;
    private Set<OilMassFractionDry> oilMassFractionDries;
    private Set<GranulesAnalyses> granulesAnalyses;
    private Set<SunProtein> proteins;

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
    public List<VROCrude> getCrudes() {
        return crudes;
    }
    public void setCrudes(List<VROCrude> crudes) {
        this.crudes = crudes;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "turn", cascade = CascadeType.ALL)
    public Set<VROOil> getOils() {
        return oils;
    }
    public void setOils(Set<VROOil> oils) {
        this.oils = oils;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "turn", cascade = CascadeType.ALL)
    public Set<VRODaily> getDailies() {
        return dailies;
    }
    public void setDailies(Set<VRODaily> dailies) {
        this.dailies = dailies;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "turn", cascade = CascadeType.ALL)
    public Set<OilMassFraction> getOilMassFractions() {
        return oilMassFractions;
    }
    public void setOilMassFractions(Set<OilMassFraction> oilMassFractions) {
        this.oilMassFractions = oilMassFractions;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "turn", cascade = CascadeType.ALL)
    public Set<OilMassFractionDry> getOilMassFractionDries() {
        return oilMassFractionDries;
    }
    public void setOilMassFractionDries(Set<OilMassFractionDry> oilMassFractionDries) {
        this.oilMassFractionDries = oilMassFractionDries;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "turn", cascade = CascadeType.ALL)
    public Set<GranulesAnalyses> getGranulesAnalyses() {
        return granulesAnalyses;
    }
    public void setGranulesAnalyses(Set<GranulesAnalyses> granulesAnalyses) {
        this.granulesAnalyses = granulesAnalyses;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "turn", cascade = CascadeType.ALL)
    public Set<SunProtein> getProteins() {
        return proteins;
    }
    public void setProteins(Set<SunProtein> proteins) {
        this.proteins = proteins;
    }

    @Override
    public JSONObject toJson(int level) {
        JSONObject json = turn.toJson();
        json.put(CRUDES, crudes());
        json.put(OIL, oil());
        json.put(DAILIES, dailies());
        json.put(OIL_MASS, oilMass());
        json.put(OIL_MASS_DRY, oilMassDry());
        json.put(GRANULAS, granules());
        json.put(PROTEIN, proteins());
        return json;
    }

    private JSONArray proteins() {
        JSONArray array = pool.getArray();
        for (SunProtein protein : proteins){
            array.add(protein.toJson());
        }
        return array;
    }

    private JSONArray granules() {
        JSONArray array = pool.getArray();
        for (GranulesAnalyses granules : granulesAnalyses){
            array.add(granules.toJson());
        }
        return array;
    }

    private JSONArray oilMassDry() {
        JSONArray array = pool.getArray();
        for (OilMassFractionDry dry : oilMassFractionDries){
            array.add(dry.toJson());
        }
        return array;
    }

    private JSONArray oilMass() {
        JSONArray array = pool.getArray();
        for (OilMassFraction fraction : oilMassFractions){
            array.add(fraction.toJson());
        }
        return array;
    }

    private JSONArray dailies() {
        JSONArray array = pool.getArray();
        for (VRODaily daily : dailies){
            array.add(daily.toJson());
        }
        return array;
    }

    private JSONArray oil() {
        JSONArray array = pool.getArray();
        for (VROOil oil : oils){
            array.add(oil.toJson());
        }
        return array;
    }

    private JSONArray crudes() {
        JSONArray array = pool.getArray();
        for (VROCrude crude : crudes){
            array.add(crude.toJson());
        }
        return array;
    }
}
