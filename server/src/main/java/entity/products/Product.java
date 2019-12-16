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
    private ProductGroup productGroup;
    private String name;
    private AnalysesType analysesType;
    private Unit unit;
    private float weight;
    private float pallet;
    private boolean wholeSale;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @OneToOne
    @JoinColumn(name = "product_group")
    public ProductGroup getProductGroup() {
        return productGroup;
    }
    public void setProductGroup(ProductGroup productGroup) {
        this.productGroup = productGroup;
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
    @Column(name = "weight")
    public float getWeight() {
        return weight;
    }
    public void setWeight(float weight) {
        this.weight = weight;
    }

    @Basic
    @Column(name = "pallet")
    public float getPallet() {
        return pallet;
    }
    public void setPallet(float pallet) {
        this.pallet = pallet;
    }

    @Basic
    @Column(name = "whole")
    public boolean getWholeSale() {
        return wholeSale;
    }
    public void setWholeSale(boolean wholeSave) {
        this.wholeSale = wholeSave;
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
    public String toString() {
        return "Product{\n" +
                "\tid=" + id + ",\n" +
                "\tproductGroup=" + productGroup + ",\n" +
                "\tname='" + name + "'\n" +
                "\tanalyses='" + analysesType + "'\n" +
                '}';
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = pool.getObject();
        json.put(ID, id);
        json.put(NAME, name);
        if (productGroup != null){
            json.put(GROUP, productGroup.toJson());
        }
        if (unit != null) {
            json.put(UNIT, unit.toJson());
        }
        json.put(PALLET, pallet);
        return json;
    }
}
