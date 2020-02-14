package entity.documents;

import constants.Constants;
import entity.DealType;
import entity.organisations.Organisation;
import entity.products.Product;
import entity.Worker;
import entity.transport.ActionTime;
import entity.weight.Unit;

import javax.persistence.*;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@Entity
@Table(name = "deal_products")
public class DealProduct implements Constants{
    private int id;
    private DealType type;
    private Product product;
    private Unit unit;
    private Shipper shipper;
    private Organisation counterparty;
    private float price;
    private String uid;
    private ActionTime create;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = TYPE)
    public DealType getType() {
        return type;
    }
    public void setType(DealType type) {
        this.type = type;
    }

    @OneToOne
    @JoinColumn(name = PRODUCT)
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }

    @OneToOne
    @JoinColumn(name = UNIT)
    public Unit getUnit() {
        return unit;
    }
    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    @OneToOne
    @JoinColumn(name = SHIPPER)
    public Shipper getShipper() {
        return shipper;
    }
    public void setShipper(Shipper shipper) {
        this.shipper = shipper;
    }

    @OneToOne
    @JoinColumn(name = COUNTERPARTY)
    public Organisation getCounterparty() {
        return counterparty;
    }
    public void setCounterparty(Organisation counterparty) {
        this.counterparty = counterparty;
    }

    @Basic
    @Column(name = PRICE)
    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }

    @Basic
    @Column(name = UID)
    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }

    @OneToOne
    @JoinColumn(name = CREATE)
    public ActionTime getCreate() {
        return create;
    }
    public void setCreate(ActionTime create) {
        this.create = create;
    }
}
