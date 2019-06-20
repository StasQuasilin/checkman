package entity.products;

import javax.persistence.*;

/**
 * Created by szpt_user045 on 20.06.2019.
 */
@Entity
@Table(name = "product_properties")
public class ProductProperty {
    private int id;
    private Product product;
    private String key;
    private String value;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "product")
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }

    @Basic
    @Column(name = "key")
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }

    @Basic
    @Column(name = "value")
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
}
