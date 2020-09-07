package entity.references;

import entity.deals.DealType;

import javax.persistence.*;

@Entity
@Table(name = "product_actions")
public class ProductAction {
    private int id;
    private Product product;
    private DealType type;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @OneToOne
    @JoinColumn(name = "_product")
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "_action")
    public DealType getType() {
        return type;
    }
    public void setType(DealType type) {
        this.type = type;
    }
}
