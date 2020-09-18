package entity.transportations;

import constants.Keys;
import entity.analyses.OilAnalyses;
import entity.analyses.SunAnalyses;
import entity.deals.DealProduct;
import entity.rails.Carriage;
import entity.weight.Weight;
import org.json.simple.JSONObject;
import utils.json.JsonAble;

import javax.persistence.*;

@Entity
@Table(name = "transportation_products")
public class TransportationProduct extends JsonAble {
    private int id;
    private TransportationDocument document;
    private Carriage carriage;
    private DealProduct dealProduct;
    private float amount;
    private Weight weight;
    private SunAnalyses sunAnalyses;
    private OilAnalyses oilAnalyses;

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
    public TransportationDocument getDocument() {
        return document;
    }
    public void setDocument(TransportationDocument document) {
        this.document = document;
    }

    @OneToOne
    @JoinColumn(name = "_carriage")
    public Carriage getCarriage() {
        return carriage;
    }
    public void setCarriage(Carriage carriage) {
        this.carriage = carriage;
    }

    @OneToOne
    @JoinColumn(name = "_deal_product")
    public DealProduct getDealProduct() {
        return dealProduct;
    }
    public void setDealProduct(DealProduct dealProduct) {
        this.dealProduct = dealProduct;
    }

    @Basic
    @Column(name = "_amount")
    public float getAmount() {
        return amount;
    }
    public void setAmount(float amount) {
        this.amount = amount;
    }

    @OneToOne
    @JoinColumn(name = "_weight")
    public Weight getWeight() {
        return weight;
    }
    public void setWeight(Weight weight) {
        this.weight = weight;
    }

//    @OneToOne
//    @JoinColumn(name = "_sun_analyses")
    @Transient
    public SunAnalyses getSunAnalyses() {
        return sunAnalyses;
    }
    public void setSunAnalyses(SunAnalyses sunAnalyses) {
        this.sunAnalyses = sunAnalyses;
    }

//    @OneToOne
//    @JoinColumn(name = "_oil_analyses")
    @Transient
    public OilAnalyses getOilAnalyses() {
        return oilAnalyses;
    }
    public void setOilAnalyses(OilAnalyses oilAnalyses) {
        this.oilAnalyses = oilAnalyses;
    }

    @Override
    public JSONObject toJson() {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put(Keys.ID, id);
        if (carriage != null){
            jsonObject.put(Keys.CARRIAGE, carriage.toShortJson());
        }
        jsonObject.put(Keys.DEAL_PRODUCT, dealProduct.getId());
        jsonObject.put(Keys.PRODUCT, dealProduct.getProduct().toShortJson());
        jsonObject.put(Keys.AMOUNT, amount);
        jsonObject.put(Keys.PRICE, dealProduct.getPrice());
        jsonObject.put(Keys.SHIPPER, dealProduct.getShipper().toJson());
        jsonObject.put(Keys.UNIT, dealProduct.getUnit().getName());
        if (weight != null){
            jsonObject.put(Keys.WEIGHT, weight.toJson());
        }
        return jsonObject;
    }
}
