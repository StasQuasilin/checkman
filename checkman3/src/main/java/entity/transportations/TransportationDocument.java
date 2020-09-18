package entity.transportations;

import constants.Keys;
import entity.deals.DealDocument;
import entity.references.Address;
import entity.references.Organisation;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.json.JsonAble;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "transportation_documents")
public class TransportationDocument extends JsonAble {
    private int id;
    private Transportation transportation;
    private DealDocument dealDocument;
    private Address address;
    private Set<TransportationProduct> products = new HashSet<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
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
    @JoinColumn(name = "_deal_document")
    public DealDocument getDealDocument() {
        return dealDocument;
    }
    public void setDealDocument(DealDocument dealDocument) {
        this.dealDocument = dealDocument;
    }

    @OneToOne
    @JoinColumn(name = "_address")
    public Address getAddress() {
        return address;
    }
    public void setAddress(Address address) {
        this.address = address;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "document", cascade = CascadeType.ALL)
    public Set<TransportationProduct> getProducts() {
        return products;
    }
    public void setProducts(Set<TransportationProduct> products) {
        this.products = products;
    }

    @Override
    public JSONObject toJson() {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put(Keys.ID, id);
        jsonObject.put(Keys.DEAL_DOCUMENT, dealDocument.getId());
        jsonObject.put(Keys.DEAL_NUMBER, dealDocument.getDeal().getNumber());
        jsonObject.put(Keys.DEAL_DATE, dealDocument.getDeal().getDate().toString());

        final Organisation counterparty = dealDocument.getCounterparty();
        if (counterparty != null){
            jsonObject.put(Keys.COUNTERPARTY, counterparty.toJson());
        }

        jsonObject.put(Keys.PRODUCTS, products());
        return jsonObject;
    }

    private JSONArray products() {
        final JSONArray array = new JSONArray();
        for (TransportationProduct product : products){
            array.add(product.toJson());
        }
        return array;
    }
}
