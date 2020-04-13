package entity.weight;

import entity.JsonAble;
import entity.transport.ActionTime;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.util.Set;

@Entity
@Table(name = "reports")
public class Report extends JsonAble {
    private int id;
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "report", cascade = CascadeType.ALL)
    public Set<SubdivisionReport> getReports() {
        return reports;
    }
    public void setReports(Set<SubdivisionReport> reports) {
        this.reports = reports;
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
        object.put(DATE, date.toString());
        object.put(TIME, time.toString());
        object.put(REPORTS, reports());
        object.put(CREATE, createTime.toJson());
        return object;
    }

    private JSONArray reports() {
        JSONArray array = pool.getArray();
        for (SubdivisionReport report : reports){
            array.add(report.toJson());
        }
        return array;
    }
}
