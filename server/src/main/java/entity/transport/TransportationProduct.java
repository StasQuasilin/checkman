package entity.transport;

import entity.JsonAble;
import entity.deal.ContractProduct;
import entity.weight.Weight;
import org.json.simple.JSONObject;

import javax.persistence.*;

/**
 * Created by szpt_user045 on 08.11.2019.
 */
@Entity
@Table(name = "_transportation_products")
public class TransportationProduct extends JsonAble{
    private int id;
    private TransportationDocument document;
    private ContractProduct contractProduct;
    private float plan;
    private Weight weight;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "document")
    public TransportationDocument getDocument() {
        return document;
    }
    public void setDocument(TransportationDocument document) {
        this.document = document;
    }

    @ManyToOne
    @JoinColumn(name = "product")
    public ContractProduct getContractProduct() {
        return contractProduct;
    }
    public void setContractProduct(ContractProduct contractProduct) {
        this.contractProduct = contractProduct;
    }

    @Basic
    @Column(name = "plan")
    public float getPlan() {
        return plan;
    }
    public void setPlan(float plan) {
        this.plan = plan;
    }

    @OneToOne
    @JoinColumn(name = "weight")
    public Weight getWeight() {
        return weight;
    }
    public void setWeight(Weight weight) {
        this.weight = weight;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = transportation.toJson();
        json.put(ID, id);
        json.put(TYPE, contractProduct.getType().toString());
        json.put(PLAN, plan);
        json.put(COUNTERPARTY, contractProduct.getContract().getCounterparty().toJson());
        if (weight != null) {
            json.put(WEIGHT, weight.toJson());
        } else {
            json.put(WEIGHT, EMPTY_JSON);
        }
        json.put(ANALYSES_TYPE, contractProduct.getProduct().getAnalysesType().toString());
        json.put(PRODUCT, contractProduct.getProduct().toJson());
        json.put(PRICE, contractProduct.getPrice());
        return json;
    }
}
