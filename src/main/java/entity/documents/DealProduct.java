package entity.documents;

import constants.Constants;
import entity.JsonAble;
import entity.organisations.Address;
import entity.products.Product;
import entity.transport.ActionTime;
import entity.weight.Unit;
import org.json.simple.JSONObject;

import javax.persistence.*;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@Entity
@Table(name = "deal_products")
public class DealProduct extends JsonAble implements Constants {
    private int id;
    private Deal deal;
    private Product product;    //!
    private float quantity;     //!
    private Unit unit;          //!
    private Shipper shipper;    //!
    private float price;        //!
    private float done;
    private Address address;
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

    @ManyToOne
    @JoinColumn(name = DEAL)
    public Deal getDeal() {
        return deal;
    }
    public void setDeal(Deal deal) {
        this.deal = deal;
    }

    @OneToOne
    @JoinColumn(name = PRODUCT)
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }

    @Basic
    @Column(name = QUANTITY)
    public float getQuantity() {
        return quantity;
    }
    public void setQuantity(float quantity) {
        this.quantity = quantity;
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

    @Basic
    @Column(name = PRICE)
    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }

    @Basic
    @Column(name = "_done")
    public float getDone() {
        return done;
    }
    public void setDone(float done) {
        this.done = done;
    }

    @OneToOne
    @JoinColumn(name = "_address")
    public Address getAddress() {
        return address;
    }
    public void setAddress(Address address) {
        this.address = address;
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
    @JoinColumn(name = CREATED)
    public ActionTime getCreate() {
        return create;
    }
    public void setCreate(ActionTime create) {
        this.create = create;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public JSONObject toJson(int level) {
        final JSONObject object = new JSONObject();
        object.put(ID, id);
        object.put(PRODUCT_ID, product.getId());
        object.put(PRODUCT_NAME, product.getName());
        object.put(PRODUCT, product.toShortJson());
        object.put(QUANTITY, quantity);
        object.put(UNIT_ID, unit.getId());
        object.put(UNIT_NAME, unit.getName());
        object.put(SHIPPER_ID, shipper.getId());
        object.put(SHIPPER_NAME, shipper.getValue());
        object.put(PRICE, price);
        return object;
    }
    @Transient
    public boolean isOver() {
        return quantity >= done;
    }
}
