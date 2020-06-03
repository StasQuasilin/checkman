package stanislav.vasilina.speditionclient.entity;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static stanislav.vasilina.speditionclient.constants.Keys.DONE;
import static stanislav.vasilina.speditionclient.constants.Keys.DRIVER;
import static stanislav.vasilina.speditionclient.constants.Keys.EXPENSES;
import static stanislav.vasilina.speditionclient.constants.Keys.FARE;
import static stanislav.vasilina.speditionclient.constants.Keys.FIELDS;
import static stanislav.vasilina.speditionclient.constants.Keys.ID;
import static stanislav.vasilina.speditionclient.constants.Keys.LEAVE;
import static stanislav.vasilina.speditionclient.constants.Keys.PER_DIEM;
import static stanislav.vasilina.speditionclient.constants.Keys.PRODUCT;
import static stanislav.vasilina.speditionclient.constants.Keys.ROUTE;
import static stanislav.vasilina.speditionclient.constants.Keys.SYNC;
import static stanislav.vasilina.speditionclient.constants.Keys.UID;

public class Report extends JsonAble implements Serializable, Comparable<Report> {
    private int id;
    private String uuid;
    private Calendar leaveTime;
    private Calendar doneDate;
    private Driver driver;
    private Route route;
    private Product product;
    private int fare;
    private int expenses;
    private int perDiem;
    final private ArrayList<ReportField> fields = new ArrayList<>();
    private boolean done;
    private boolean sync;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Calendar getLeaveTime() {
        return leaveTime;
    }
    public void setLeaveTime(Calendar leaveTime) {
        this.leaveTime = leaveTime;
    }

    public Calendar getDoneDate() {
        return doneDate;
    }
    public void setDoneDate(Calendar doneDate) {
        this.doneDate = doneDate;
    }

    public Driver getDriver() {
        return driver;
    }
    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Route getRoute() {
        return route;
    }
    public void setRoute(Route route) {
        this.route = route;
    }

    public boolean isDone() {
        return done;
    }
    public void setDone(boolean done) {
        this.done = done;
    }

    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }

    public int getFare() {
        return fare;
    }
    public void setFare(int fare) {
        this.fare = fare;
    }

    public int getExpenses() {
        return expenses;
    }
    public void setExpenses(int expenses) {
        this.expenses = expenses;
    }

    public int getPerDiem() {
        return perDiem;
    }
    public void setPerDiem(int perDiem) {
        this.perDiem = perDiem;
    }

    public boolean isSync() {
        return sync;
    }

    public void setSync(boolean sync) {
        this.sync = sync;
    }

    public void addField(ReportField field){
        fields.add(field);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put(ID, id);
        json.put(UID, uuid);
        if (leaveTime != null) {
            json.put(LEAVE, leaveTime.getTimeInMillis());
        }
        if(doneDate != null){
            json.put(DONE, doneDate.getTimeInMillis());
        }
        if (driver != null){
            json.put(DRIVER, driver.toJson());
        }
        if (route != null){
            json.put(ROUTE, route.toJson());
        }
        if (product != null){
            json.put(PRODUCT, product.getId());
        }
        json.put(FIELDS, fields());
        json.put(FARE, fare);
        json.put(EXPENSES, expenses);
        json.put(PER_DIEM, perDiem);
        json.put(SYNC, sync);
        return json;
    }

    private JSONArray fields() {
        JSONArray array = new JSONArray();
        for (ReportField field : fields){
            array.add(field.toJson());
        }
        return array;
    }

    public List<ReportField> getFields() {
        return fields;
    }

    @Override
    public int compareTo(Report o) {
        return o.leaveTime.compareTo(leaveTime);
    }
}
