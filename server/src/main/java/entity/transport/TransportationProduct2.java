package entity.transport;

import entity.JsonAble;
import entity.deal.ContractProduct;
import entity.products.Product;
import org.json.simple.JSONObject;

import javax.persistence.*;

/**
 * Created by szpt_user045 on 08.11.2019.
 */
@Entity
@Table(name = "_transportation_products")
public class TransportationProduct2 extends JsonAble{
    private int id;
    private TransportationDocument2 document;
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
    public TransportationDocument2 getDocument() {
        return document;
    }
    public void setDocument(TransportationDocument2 document) {
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
        JSONObject json = pool.getObject();
        json.put(ID, id);
        json.put(TYPE, contractProduct.getType().toString());
        json.put(AMOUNT, amount);
        json.put(UNIT, contractProduct.getUnit().toJson());
        Product product = contractProduct.getProduct();
        json.put(PRODUCT, product.toJson());
        if (product.getAnalysesType() != null){
            json.put(ANALYSES_TYPE, product.getAnalysesType().toString());
        }
        json.put(PRICE, contractProduct.getPrice());
        json.put(SHIPPER, contractProduct.getShipper().toJson());
        return json;
    }
}
