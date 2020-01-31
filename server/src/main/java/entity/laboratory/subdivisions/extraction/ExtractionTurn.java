package entity.laboratory.subdivisions.extraction;

import entity.JsonAble;
import entity.production.Turn;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by szpt_user045 on 04.04.2019.
 */
@Entity
@Table(name = "turns_extraction")
public class ExtractionTurn extends JsonAble{
    private int id;
    private Turn turn;
    private List<ExtractionCrude> crudes = new ArrayList<>();
    private Set<StorageProtein> protein;
    private Set<StorageGrease> greases;
    private Set<ExtractionOIl> oils;
    private Set<TurnProtein> turnProteins;
    private Set<TurnGrease> turnGreases;
    private Set<TurnCellulose> cellulose;
    private Set<MealGranules> granules;

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
    public List<ExtractionCrude> getCrudes() {
        return crudes;
    }
    public void setCrudes(List<ExtractionCrude> crudes) {
        this.crudes = crudes;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "turn", cascade = CascadeType.ALL)
    public Set<StorageProtein> getProtein() {
        return protein;
    }
    public void setProtein(Set<StorageProtein> raws) {
        this.protein = raws;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "turn", cascade = CascadeType.ALL)
    public Set<StorageGrease> getGreases() {
        return greases;
    }
    public void setGreases(Set<StorageGrease> grease) {
        this.greases = grease;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "turn", cascade = CascadeType.ALL)
    public Set<ExtractionOIl> getOils() {
        return oils;
    }
    public void setOils(Set<ExtractionOIl> oil) {
        this.oils = oil;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "turn", cascade = CascadeType.ALL)
    public Set<TurnProtein> getTurnProteins() {
        return turnProteins;
    }
    public void setTurnProteins(Set<TurnProtein> turns) {
        this.turnProteins = turns;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "turn", cascade = CascadeType.ALL)
    public Set<TurnGrease> getTurnGreases() {
        return turnGreases;
    }
    public void setTurnGreases(Set<TurnGrease> turnGreases) {
        this.turnGreases = turnGreases;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "turn", cascade = CascadeType.ALL)
    public Set<TurnCellulose> getCellulose() {
        return cellulose;
    }
    public void setCellulose(Set<TurnCellulose> cellulose) {
        this.cellulose = cellulose;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "turn", cascade = CascadeType.ALL)
    public Set<MealGranules> getGranules() {
        return granules;
    }
    public void setGranules(Set<MealGranules> granules) {
        this.granules = granules;
    }

    @Override
    public JSONObject toJson() {
        JSONObject object = turn.toJson();
        object.put(CRUDE, crudesJson());
        object.put(STORAGE_PROTEIN, storageProtein());
        object.put(STORAGE_GREASE, storageGrease());
        object.put(OIL, oil());
        object.put(TURN_PROTEIN, turnProtein());
        object.put(TURN_GREASE, turnGrease());
        object.put(CELLULOSE, turnCellulose());
        object.put(GRANULAS, mealGranules());
        return object;
    }

    private Object mealGranules() {
        JSONArray array = pool.getArray();
        array.addAll(granules.stream().map(MealGranules::toJson).collect(Collectors.toList()));
        return array;
    }


    @Transient
    private Object turnCellulose() {
        JSONArray array = pool.getArray();
        array.addAll(cellulose.stream().map(TurnCellulose::toJson).collect(Collectors.toList()));
        return array;
    }

    @Transient
    private Object turnGrease() {
        JSONArray array = pool.getArray();
        array.addAll(turnGreases.stream().map(TurnGrease::toJson).collect(Collectors.toList()));
        return array;
    }

    @Transient
    private Object turnProtein() {
        JSONArray array = pool.getArray();
        array.addAll(turnProteins.stream().map(TurnProtein::toJson).collect(Collectors.toList()));
        return array;
    }

    @Transient
    private Object oil() {
        JSONArray array = pool.getArray();
        array.addAll(oils.stream().map(ExtractionOIl::toJson).collect(Collectors.toList()));
        return array;
    }

    @Transient
    private Object storageGrease() {
        JSONArray array = pool.getArray();
        array.addAll(greases.stream().map(StorageGrease::toJson).collect(Collectors.toList()));
        return array;
    }

    @Transient
    private Object storageProtein() {
        JSONArray array = pool.getArray();
        array.addAll(protein.stream().map(StorageProtein::toJson).collect(Collectors.toList()));
        return array;
    }

    @Transient
    private Object crudesJson() {
        JSONArray array = pool.getArray();
        array.addAll(crudes.stream().map(ExtractionCrude::toJson).collect(Collectors.toList()));
        return array;
    }
}
