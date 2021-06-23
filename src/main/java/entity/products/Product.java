package entity.products;

import entity.AnalysesType;
import entity.JsonAble;
import entity.weight.Unit;
import org.json.simple.JSONObject;

import javax.persistence.*;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@Entity
@Table (name = "products")
public class Product extends JsonAble {
    private int id;
    private String name;
    private AnalysesType analysesType;
    private Unit unit;
    private boolean isGroup;
    private boolean wholeSale;
    private boolean refining;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "analyses")
    public AnalysesType getAnalysesType() {
        return analysesType;
    }
    public void setAnalysesType(AnalysesType analysesType) {
        this.analysesType = analysesType;
    }

    @OneToOne
    @JoinColumn(name = "unit")
    public Unit getUnit() {
        return unit;
    }
    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    @Basic
    @Column(name = "whole")
    public boolean getWholeSale() {
        return wholeSale;
    }
    public void setWholeSale(boolean wholeSave) {
        this.wholeSale = wholeSave;
    }

    @Basic
    @Column(name = "_group")
    public boolean isGroup() {
        return isGroup;
    }
    public void setGroup(boolean group) {
        isGroup = group;
    }

    @Basic
    @Column(name = "_refining")
    public boolean isRefining() {
        return refining;
    }
    public void setRefining(boolean refining) {
        this.refining = refining;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public JSONObject toShortJson() {
        JSONObject json = pool.getObject();
        json.put(ID, id);
        json.put(NAME, name);
        json.put(REFINING, refining);
        json.put(GROUP, isGroup);
        if (analysesType != null){
            json.put(ANALYSES_TYPE, analysesType.toString());
        } else {
            json.put(ANALYSES_TYPE, AnalysesType.other.toString());
        }

        return json;
    }

    @Override
    public JSONObject toJson(int level) {
        JSONObject json = toShortJson();
        if (unit != null) {
            json.put(UNIT, unit.toJson());
        }

        return json;
    }


}
