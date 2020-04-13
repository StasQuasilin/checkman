package entity.weight;

import entity.JsonAble;
import entity.Subdivision;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;

import javax.persistence.*;

@Entity
@Table(name = "subdivision_reports")
public class SubdivisionReport extends JsonAble implements Comparable<SubdivisionReport> {
    private int id;
    private RoundReport report;
    private boolean serviceability;
    private boolean adherence;
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
    @Column(name = "serviceability")
    public boolean isServiceability() {
        return serviceability;
    }
    public void setServiceability(boolean serviceability) {
        this.serviceability = serviceability;
    }

    @Basic
    @Column(name = "adherence")
    public boolean isAdherence() {
        return adherence;
    }
    public void setAdherence(boolean adherence) {
        this.adherence = adherence;
    }

    @ManyToOne
    @JoinColumn(name = "report")
    public RoundReport getReport() {
        return report;
    }
    public void setReport(RoundReport roundReport) {
        this.report = roundReport;
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
    @Column(name = "note")
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
        object.put(SUBDIVISION, subdivision.toJson());
        object.put(SERVICEABILITY, serviceability);
        if (subdivision.isTehControl()){
            object.put(ADHERENCE, adherence);
        }
        object.put(NOTE, note);
        return object;
    }

    @Override
    public int compareTo(@NotNull SubdivisionReport subdivisionReport) {
        return getSubdivision().getId() - subdivisionReport.getSubdivision().getId();
    }
}
