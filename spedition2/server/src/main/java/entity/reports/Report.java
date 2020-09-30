package entity.reports;

import constants.Keys;
import entity.user.User;
import entity.references.Product;
import org.json.simple.JSONObject;
import utils.json.JsonAble;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "reports")
public class Report extends JsonAble {
    private int id;
    private String uuid;
    private Timestamp begin;
    private Timestamp end;
    private Product product;
    private String route;
    private User owner;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "_id")
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "_uuid")
    public String getUuid() {
        return uuid;
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Basic
    @Column(name = "_begin")
    public Timestamp getBegin() {
        return begin;
    }
    public void setBegin(Timestamp begin) {
        this.begin = begin;
    }

    @Basic
    @Column(name = "_end")
    public Timestamp getEnd() {
        return end;
    }
    public void setEnd(Timestamp end) {
        this.end = end;
    }

    @OneToOne
    @JoinColumn(name = "_product")
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }

    @Basic
    @Column(name = "_route")
    public String getRoute() {
        return route;
    }
    public void setRoute(String route) {
        this.route = route;
    }

    @OneToOne
    @JoinColumn(name = "_owner")
    public User getOwner() {
        return owner;
    }
    public void setOwner(User owner) {
        this.owner = owner;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Keys.ID, id);
        jsonObject.put(Keys.UUID, uuid);
        if(begin != null) {
            jsonObject.put(Keys.BEGIN, begin.toString());
        }
        if (end != null){
            jsonObject.put(Keys.END, end.toString());
        }
        jsonObject.put(Keys.PRODUCT, product.toJson());
        jsonObject.put(Keys.ROUTE, route);
        return jsonObject;
    }
}
