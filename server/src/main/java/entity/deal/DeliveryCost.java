package entity.deal;

import constants.Constants;
import entity.JsonAble;
import entity.documents.Deal;
import entity.transport.TransportCustomer;
import org.json.simple.JSONObject;

import javax.persistence.*;

@Entity
@Table(name = "delivery_cost")
public class DeliveryCost extends JsonAble {
    private int id;
    private Deal deal;
    private TransportCustomer customer;
    private float cost;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
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

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "_customer")
    public TransportCustomer getCustomer() {
        return customer;
    }
    public void setCustomer(TransportCustomer customer) {
        this.customer = customer;
    }

    @Basic
    @Column(name = "_cost")
    public float getCost() {
        return cost;
    }
    public void setCost(float coast) {
        this.cost = coast;
    }

    @Override
    public JSONObject toJson() {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put(ID, id);
        jsonObject.put(CUSTOMER, customer.toString());
        jsonObject.put(Constants.COST, cost);
        return jsonObject;
    }
}
