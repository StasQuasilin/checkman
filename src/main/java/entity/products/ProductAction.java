package entity.products;

import constants.Constants;
import entity.DealType;
import entity.JsonAble;
import org.json.simple.JSONObject;

import javax.persistence.*;

/**
 * Created by szpt_user045 on 24.02.2020.
 */
@Entity
@Table(name = "product_types")
public class ProductAction extends JsonAble implements Constants {
    private int id;
    private Product product;
    private DealType type;
    private boolean editable;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @OneToOne
    @JoinColumn(name = PRODUCT)
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = TYPE)
    public DealType getType() {
        return type;
    }
    public void setType(DealType type) {
        this.type = type;
    }

    @Basic
    @Column(name = EDITABLE)
    public boolean getEditable() {
        return editable;
    }
    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    @Override
    public JSONObject toShortJson() {
        JSONObject object = pool.getObject();
        object.put(PRODUCT, product.toShortJson());
        object.put(TYPE, type.toString());
        object.put(EDITABLE, editable);
        return object;
    }

    @Override
    public JSONObject toJson() {
        JSONObject object = toShortJson();
        object.put(PRODUCT, product.toJson());
        return object;
    }
}
