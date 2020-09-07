package entity.deals;

import entity.references.Product;
import entity.weight.Unit;

import javax.persistence.*;

@Entity
@Table(name = "deal_products")
public class DealProduct {
    private int id;
    private DealDocument document;
    private Product product;
    private float amount;
    private Unit unit;
    private float price;
    private Shipper shipper;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "_document")
    public DealDocument getDocument() {
        return document;
    }
    public void setDocument(DealDocument document) {
        this.document = document;
    }

    @OneToOne
    @JoinColumn(name = "_product")
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }

    @Basic
    @Column(name = "_amount")
    public float getAmount() {
        return amount;
    }
    public void setAmount(float amount) {
        this.amount = amount;
    }

    @Basic
    @Column(name = "_unit")
    public Unit getUnit() {
        return unit;
    }
    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    @Basic
    @Column(name = "_price")
    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }

    @Basic
    @Column(name = "_shipper")
    public Shipper getShipper() {
        return shipper;
    }
    public void setShipper(Shipper shipper) {
        this.shipper = shipper;
    }
}
