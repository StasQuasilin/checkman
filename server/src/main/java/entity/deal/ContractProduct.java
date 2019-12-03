package entity.deal;

import entity.DealType;
import entity.JsonAble;
import entity.documents.Shipper;
import entity.products.Product;
import entity.weight.WeightUnit;
import org.json.simple.JSONObject;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by szpt_user045 on 08.11.2019.
 */
@Entity
@Table(name = "_contract_products")
public class ContractProduct extends JsonAble {
    private int id;
    private Contract contract;
    private DealType type;
    private Product product;
    private Shipper shipper;
    private float done;
    private float amount;
    private WeightUnit unit;
    private float price;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "contract")
    public Contract getContract() {
        return contract;
    }
    public void setContract(Contract contract) {
        this.contract = contract;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    public DealType getType() {
        return type;
    }
    public void setType(DealType type) {
        this.type = type;
    }

    @OneToOne
    @JoinColumn(name = "product")
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }

    @OneToOne
    @JoinColumn(name = "shipper")
    public Shipper getShipper() {
        return shipper;
    }
    public void setShipper(Shipper shipper) {
        this.shipper = shipper;
    }

    @Basic
    @Column(name = "done")
    public float getDone() {
        return done;
    }
    public void setDone(float done) {
        this.done = done;
    }

    @Basic
    @Column(name = "amount")
    public float getAmount() {
        return amount;
    }
    public void setAmount(float amount) {
        this.amount = amount;
    }

    @OneToOne
    @JoinColumn(name = "unit")
    public WeightUnit getUnit() {
        return unit;
    }
    public void setUnit(WeightUnit unit) {
        this.unit = unit;
    }

    @Basic
    @Column(name = "price")
    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = pool.getObject();
        json.put(ID, id);
        json.put(TYPE, type.toString());
        json.put(PRODUCT, product.toJson());
        json.put(SHIPPER, shipper.toJson());
        json.put(DONE, done);
        json.put(AMOUNT, amount);
        json.put(PRICE, price);
        return json;
    }
}
