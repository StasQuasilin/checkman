package stanislav.vasilina.speditionclient.entity;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static stanislav.vasilina.speditionclient.constants.Keys.DRIVER;
import static stanislav.vasilina.speditionclient.constants.Keys.FIELDS;
import static stanislav.vasilina.speditionclient.constants.Keys.ID;
import static stanislav.vasilina.speditionclient.constants.Keys.LEAVE;
import static stanislav.vasilina.speditionclient.constants.Keys.ROUTE;
import static stanislav.vasilina.speditionclient.constants.Keys.UID;

public class Report extends JsonAble implements Serializable {
    private int id;
    private String uuid;
    private Calendar leaveTime;
    private User attendant;
    private Driver driver;
    private Route route;
    private Product product;
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

    public User getAttendant() {
        return attendant;
    }
    public void setAttendant(User attendant) {
        this.attendant = attendant;
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
        if (driver != null){
            json.put(DRIVER, driver.toJson());
        }
        if (route != null){
            json.put(ROUTE, route.toJson());
        }
        json.put(FIELDS, fields());
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
}
