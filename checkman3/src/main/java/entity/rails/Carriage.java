package entity.rails;

import constants.Keys;
import entity.analyses.OilAnalyses;
import entity.deals.DealProduct;
import entity.weight.Weight;
import org.json.simple.JSONObject;
import utils.json.JsonAble;

import javax.persistence.*;

@Entity
@Table(name = "_carriages")
public class Carriage extends JsonAble {
    private int id;
    private Train train;
    private String number;
    private DealProduct dealProduct;
    private Weight weight;
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
    @JoinColumn(name = "_train")
    public Train getTrain() {
        return train;
    }
    public void setTrain(Train train) {
        this.train = train;
    }

    @Basic
    @Column(name = "_number")
    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }

    @OneToOne
    @JoinColumn(name = "_deal_product")
    public DealProduct getDealProduct() {
        return dealProduct;
    }
    public void setDealProduct(DealProduct dealProduct) {
        this.dealProduct = dealProduct;
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
        jsonObject.put(Keys.NUMBER, number);
        jsonObject.put(Keys.DEAL_PRODUCT, dealProduct.toJson());
        if (weight != null){
            jsonObject.put(Keys.WEIGHT, weight.toJson());
        }
//        if (oilAnalyses != null){
//            jsonObject.put(Keys.OIL_ANALYSES, oilAnalyses.toJson());
//        }
        return jsonObject;
    }
}
