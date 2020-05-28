package stanislav.vasilina.speditionclient.entity;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static stanislav.vasilina.speditionclient.constants.Keys.FIELDS;
import static stanislav.vasilina.speditionclient.constants.Keys.ID;
import static stanislav.vasilina.speditionclient.constants.Keys.LEAVE;
import static stanislav.vasilina.speditionclient.constants.Keys.UUID;

public class Report extends JsonAble implements Serializable {
    private int id;
    private String uuid;
    private Date leaveTime;
    private User attendant;
    private Driver driver;
    final private ArrayList<ReportField> fields = new ArrayList<>();
    private boolean done;


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

    public Date getLeaveTime() {
        return leaveTime;
    }
    public void setLeaveTime(Date leaveTime) {
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

    public boolean isDone() {
        return done;
    }
    public void setDone(boolean done) {
        this.done = done;
    }

    public void addField(ReportField field){
        fields.add(field);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put(ID, id);
        json.put(UUID, uuid);
        if (leaveTime != null) {
            json.put(LEAVE, leaveTime.toString());
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
