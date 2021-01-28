package entity.transport;

import entity.documents.Deal;
import entity.documents.DealProduct;
import entity.laboratory.MealAnalyses;
import entity.laboratory.OilAnalyses;
import entity.laboratory.SunAnalyses;
import entity.organisations.Address;
import entity.weight.Weight;

import javax.persistence.*;

/**
 * Created by szpt_user045 on 03.03.2020.
 */
@Entity
@Table(name = "transportation_products")
public class TransportationProduct {
    private int id;
    private TransportationGroup group;
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

    @ManyToOne
    @JoinColumn(name = "transportation_group")
    public TransportationGroup getGroup() {
        return group;
    }
    public void setGroup(TransportationGroup group) {
        this.group = group;
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
}
