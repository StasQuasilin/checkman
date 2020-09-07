package entity.deals;

import constants.Keys;
import entity.references.Address;
import entity.references.Organisation;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.json.JsonAble;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "deal_documents")
public class DealDocument extends JsonAble {
    private int id;
    private Deal deal;
    private Organisation counterparty;
    private Address address;
    private Set<DealProduct> products = new HashSet<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "_deal")
    public Deal getDeal() {
        return deal;
    }
    public void setDeal(Deal deal) {
        this.deal = deal;
    }

    @OneToOne
    @JoinColumn(name = "_counterparty")
    public Organisation getCounterparty() {
        return counterparty;
    }
    public void setCounterparty(Organisation counterparty) {
        this.counterparty = counterparty;
    }


    @Transient
//    @OneToOne
//    @JoinColumn(name = "_address")
    public Address getAddress() {
        return address;
    }
    public void setAddress(Address address) {
        this.address = address;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "document", cascade = CascadeType.ALL)
    public Set<DealProduct> getProducts() {
        return products;
    }
    public void setProducts(Set<DealProduct> products) {
        this.products = products;
    }

    @Override
    public JSONObject toJson() {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put(Keys.ID, id);
        jsonObject.put(Keys.COUNTERPARTY, counterparty.toJson());
        jsonObject.put(Keys.ADDRESS, null);
        jsonObject.put(Keys.PRODUCTS, products());
        return jsonObject;
    }

    private JSONArray products() {
        final JSONArray array = new JSONArray();
        for (DealProduct product : products){
            array.add(product.toJson());
        }
        return array;
    }
}
