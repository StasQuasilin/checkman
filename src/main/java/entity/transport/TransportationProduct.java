package entity.transport;

import entity.JsonAble;
import entity.documents.DealProduct;
import entity.laboratory.MealAnalyses;
import entity.laboratory.OilAnalyses;
import entity.laboratory.SunAnalyses;
import entity.organisations.Address;
import entity.weight.Weight;
import org.json.simple.JSONObject;

import javax.persistence.*;

/**
 * Created by szpt_user045 on 03.03.2020.
 */
@Entity
@Table(name = "transportation_products")
public class TransportationProduct extends JsonAble {
    private int id;
    private int index;
    private Transportation transportation;
    private DealProduct dealProduct;
    private float amount;
    private Weight weight;
    private String uid;
    private SunAnalyses sunAnalyses;
    private OilAnalyses oilAnalyses;
    private MealAnalyses mealAnalyses;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "_index")
    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }

    @ManyToOne
    @JoinColumn(name = "_transportation")
    public Transportation getTransportation() {
        return transportation;
    }
    public void setTransportation(Transportation transportation) {
        this.transportation = transportation;
    }

    @OneToOne
    @JoinColumn(name = "deal_product")
    public DealProduct getDealProduct() {
        return dealProduct;
    }
    public void setDealProduct(DealProduct dealProduct) {
        this.dealProduct = dealProduct;
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
    @JoinColumn(name = "weight")
    public Weight getWeight() {
        return weight;
    }
    public void setWeight(Weight weight) {
        this.weight = weight;
    }

    @Basic
    @Column(name = "uid")
    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }

    @OneToOne
    @JoinColumn(name = "sun")
    public SunAnalyses getSunAnalyses() {
        return sunAnalyses;
    }
    public void setSunAnalyses(SunAnalyses sunAnalyses) {
        this.sunAnalyses = sunAnalyses;
    }

    @OneToOne
    @JoinColumn(name = "oil")
    public OilAnalyses getOilAnalyses() {
        return oilAnalyses;
    }
    public void setOilAnalyses(OilAnalyses oilAnalyses) {
        this.oilAnalyses = oilAnalyses;
    }

    @OneToOne
    @JoinColumn(name = "meal")
    public MealAnalyses getMealAnalyses() {
        return mealAnalyses;
    }
    public void setMealAnalyses(MealAnalyses mealAnalyses) {
        this.mealAnalyses = mealAnalyses;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public JSONObject toJson(int level) {
        final JSONObject object = new JSONObject();
        object.put(INDEX, index);
        object.put(ID, id);
        object.put(DEAL, dealProduct.getDeal().getId());
        object.put(DEAL_PRODUCT, dealProduct.getId());
        object.put(PRODUCT_ID, dealProduct.getProduct().getId());
        object.put(PRODUCT_NAME, dealProduct.getProduct().getName());
        object.put(PRODUCT, dealProduct.getProduct().toShortJson());
        object.put(COUNTERPARTY, dealProduct.getDeal().getOrganisation().toJson(level));
        final Address address = dealProduct.getAddress();
        if (address != null){
            object.put(ADDRESS_ID, address.getId());
            object.put(ADDRESS, address.toJson(level));
        }
        object.put(QUANTITY, dealProduct.getQuantity());
        object.put(AMOUNT, amount);
        object.put(UNIT_ID, dealProduct.getUnit().getId());
        object.put(UNIT_NAME, dealProduct.getUnit().getName());
        object.put(PRICE, dealProduct.getPrice());
        object.put(SHIPPER_ID, dealProduct.getShipper().getId());
        object.put(SHIPPER_NAME, dealProduct.getShipper().getValue());
        if (weight != null) {
            object.put(WEIGHT, weight.toJson(level));
        }
        return object;
    }
}
