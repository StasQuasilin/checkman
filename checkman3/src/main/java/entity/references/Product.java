package entity.references;

import entity.analyses.AnalysesType;
import entity.weight.Unit;
import org.json.simple.JSONObject;
import utils.json.JsonAble;

import javax.persistence.*;

import static constants.Keys.*;

@Entity
@Table(name = "products")
public class Product extends JsonAble {
    private int id;
    private String name;
    private AnalysesType analysesType;
    private Unit unit;
    private Product parent;
    private boolean group;
    private boolean main;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "_name")
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "_analyses_type")
    public AnalysesType getAnalysesType() {
        return analysesType;
    }
    public void setAnalysesType(AnalysesType analysesType) {
        this.analysesType = analysesType;
    }

    @OneToOne
    @JoinColumn(name = "_unit")
    public Unit getUnit() {
        return unit;
    }
    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    @OneToOne
    @JoinColumn(name = "_parent")
    public Product getParent() {
        return parent;
    }
    public void setParent(Product parent) {
        this.parent = parent;
    }

    @Basic
    @Column(name = "_group")
    public boolean isGroup() {
        return group;
    }
    public void setGroup(boolean group) {
        this.group = group;
    }

    @Basic
    @Column(name = "_main")
    public boolean isMain() {
        return main;
    }
    public void setMain(boolean main) {
        this.main = main;
    }

    @Override
    public JSONObject toShortJson() {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put(ID, id);
        jsonObject.put(NAME, name);


        return jsonObject;
    }

    @Override
    public JSONObject toJson() {
        final JSONObject jsonObject = toShortJson();
        jsonObject.put(GROUP, group);
        if (analysesType != null) {
            jsonObject.put(ANALYSES_TYPE, analysesType);
        }
        return jsonObject;
    }

    @Override
    public int hashCode() {
        if (id > 0){
            return id;
        } else {
            return name.hashCode();
        }
    }
}
