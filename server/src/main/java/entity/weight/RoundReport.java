package entity.weight;

import entity.JsonAble;
import entity.transport.ActionTime;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;

@Entity
@Table(name = "round_reports")
public class RoundReport extends JsonAble {
    private int id;
    private Timestamp timestamp;
    private Date date;
    private Time time;
    private Set<SubdivisionReport> reports;
    private ActionTime createTime;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "_timestamp")
    public Timestamp getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Basic
    @Column(name = "_date")
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    @Basic
    @Column(name = "_time")
    public Time getTime() {
        return time;
    }
    public void setTime(Time time) {
        this.time = time;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "report", cascade = CascadeType.ALL)
    public Set<SubdivisionReport> getReports() {
        return reports;
    }
    public void setReports(Set<SubdivisionReport> reports) {
        this.reports = reports;
    }

    @Transient
    public ArrayList<SubdivisionReport> sortedReports(){
        ArrayList<SubdivisionReport> list = new ArrayList<>(reports);
        Collections.sort(list);
        return list;
    }

    @OneToOne
    @JoinColumn(name = "create_time")
    public ActionTime getCreateTime() {
        return createTime;
    }
    public void setCreateTime(ActionTime createTime) {
        this.createTime = createTime;
    }

    @Override
    public JSONObject toJson() {
        JSONObject object = pool.getObject();
        object.put(ID, id);
        object.put("timestamp", timestamp.toString());
        object.put(REPORTS, reports());
        object.put(CREATE, createTime.toJson());
        return object;
    }

    private JSONArray reports() {
        JSONArray array = pool.getArray();
        for (SubdivisionReport report : sortedReports()){
            array.add(report.toJson());
        }
        return array;
    }
}
