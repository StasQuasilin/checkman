package entity.weight;

import entity.JsonAble;
import entity.Subdivision;
import org.json.simple.JSONObject;

import javax.persistence.*;

@Entity
@Table(name = "subdivision_reports")
public class SubdivisionReport extends JsonAble {
    private int id;
    private Report report;
    private boolean good;
    private Subdivision subdivision;
    private String note;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "no_observation")
    public boolean isGood() {
        return good;
    }
    public void setGood(boolean good) {
        this.good = good;
    }

    @ManyToOne
    @JoinColumn(name = "report")
    public Report getReport() {
        return report;
    }
    public void setReport(Report report) {
        this.report = report;
    }

    @OneToOne
    @JoinColumn(name = "subdivision")
    public Subdivision getSubdivision() {
        return subdivision;
    }
    public void setSubdivision(Subdivision subdivision) {
        this.subdivision = subdivision;
    }

    @Basic
    @Column(name = "notes")
    public String getNote() {
        return note;
    }
    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public JSONObject toJson() {
        JSONObject object = pool.getObject();
        object.put(ID, id);
        object.put(GOOD, good);
        object.put(NOTE, note);
        return object;
    }
}
