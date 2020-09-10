package entity.transportations;

import constants.Keys;
import entity.references.Address;
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
