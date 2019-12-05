package entity.transport;

import entity.Address;
import entity.JsonAble;
import entity.organisations.LoadAddress;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.persistence.*;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by szpt_user045 on 29.11.2019.
 */
@Entity
@Table(name = "transportation_documents")
public class TransportationDocument extends JsonAble{
    private int id;
    private Transportation2 transportation;
    private int index;
    private Address address;
    private Set<TransportationProduct> products;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "transportation")
    public Transportation2 getTransportation() {
        return transportation;
    }
    public void setTransportation(Transportation2 transportation) {
        this.transportation = transportation;
    }

    @Basic
    @Column(name = "idx")
    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }

    @OneToOne
    @JoinColumn(name = "address")
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
        JSONObject json = pool.getObject();
        json.put(ID, id);
        json.put(ADDRESS, address.toJson());
        JSONArray array = pool.getArray();
        array.addAll(products.stream().map(TransportationProduct::toJson).collect(Collectors.toList()));
        json.put(PRODUCTS, array);
        return json;
    }
}
