package entity;

import org.json.simple.JSONObject;

import javax.persistence.*;
import java.sql.Timestamp;

import static constants.Keys.*;

@Entity
@Table(name = "reports")
public class Report extends JsonAble {
    private int id;
    private String uuid;
    private Timestamp leaveTime;
    private Product product;
    private String route;
    private User owner;
    private Driver driver;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "uuid")
    public String getUuid() {
        return uuid;
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Basic
    @Column(name = "leave_time")
    public Timestamp getLeaveTime() {
        return leaveTime;
    }
    public void setLeaveTime(Timestamp leaveTime) {
        this.leaveTime = leaveTime;
    }

    @OneToOne
    @JoinColumn(name = "product")
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }

    @Basic
    @Column(name = "route")
    public String getRoute() {
        return route;
    }
    public void setRoute(String route) {
        this.route = route;
    }

    @OneToOne
    @JoinColumn(name = "owner")
    public User getOwner() {
        return owner;
    }
    public void setOwner(User attendant) {
        this.owner = attendant;
    }

    @OneToOne
    @JoinColumn(name = "driver")
    public Driver getDriver() {
        return driver;
    }
    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = getJsonObject();
        json.put(ID, id);
        json.put(UUID, uuid);
        json.put(LEAVE, leaveTime.toString());

        if (product != null){
            json.put(PRODUCT, product.toJson());
        }
        if (route != null){
            json.put(ROUTE, route);
        }

        json.put(OWNER, owner.toJson());
        return json;
    }
}
