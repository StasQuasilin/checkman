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
    private float amount;

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
    @Column(name = "amount")
    public float getAmount() {
        return amount;
    }
    public void setAmount(float amount) {
        this.amount = amount;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = document.toJson();
        json.put(ID, id);
        json.put(TYPE, contractProduct.getType().toString());
        json.put(AMOUNT, amount);
        json.put(COUNTERPARTY, contractProduct.getContract().getCounterparty().toJson());

        json.put(ANALYSES_TYPE, contractProduct.getProduct().getAnalysesType().toString());
        json.put(PRODUCT, contractProduct.getProduct().toJson());
        json.put(PRICE, contractProduct.getPrice());
        return json;
    }
}
