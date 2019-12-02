package entity.products;

import entity.AnalysesType;
import entity.JsonAble;
import entity.weight.WeightUnit;
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
    private WeightUnit unit;

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

    @Override
    public int hashCode() {
        return id;
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
        return json;
    }
}
