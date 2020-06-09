package entity;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import static constants.Keys.*;

@Entity
@Table(name = "reports")
public class Report extends JsonAble {
    private int id;
    private String uuid;
    private Timestamp leaveTime;
    private Product product;
    private String route;
    private Timestamp done;
    private User owner;
    private Driver driver;
    private int fare;
    private Set<Expense> expenses = new HashSet<>();
    private int perDiem;

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

    @Basic
    @Column(name = "done")
    public Timestamp getDone() {
        return done;
    }
    public void setDone(Timestamp done) {
        this.done = done;
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

    @Basic
    @Column(name = "fare")
    public int getFare() {
        return fare;
    }
    public void setFare(int fare) {
        this.fare = fare;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "report", cascade = CascadeType.ALL)
    public Set<Expense> getExpenses() {
        return expenses;
    }
    public void setExpenses(Set<Expense> expenses) {
        this.expenses = expenses;
    }

    @Basic
    @Column(name = "per_diem")
    public int getPerDiem() {
        return perDiem;
    }
    public void setPerDiem(int perDiem) {
        this.perDiem = perDiem;
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

        if (done != null){
            json.put(DONE, done.toString());
        }

        json.put(OWNER, owner.toJson());
        json.put(FARE, fare);
        json.put(EXPENSES, expenses());
        json.put(PER_DIEM, perDiem);
        return json;
    }

    private JSONArray expenses() {
        JSONArray array = new JSONArray();
        for (Expense expense : expenses){
            array.add(expense.toJson());
        }
        return array;
    }

    static final int divider = 1000 * 60 * 60 * 24;
    @Transient
    public int length(){
        if (leaveTime != null && done != null){
            final long diff = done.getTime() - leaveTime.getTime();
            return (int) Math.ceil(1d * diff / divider);
        }
        return 0;
    }
}
