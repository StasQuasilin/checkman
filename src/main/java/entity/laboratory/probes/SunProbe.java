package entity.laboratory.probes;

import entity.laboratory.SunAnalyses;
import org.json.simple.JSONObject;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by quasilin on 01.04.2019.
 */
@Entity
@Table(name = "probe_sun")
public class SunProbe extends IProbe {
    private int id;
    private ProbeTurn turn;
    private Date date;
    private String manager;
    private String organisation;
    private SunAnalyses analyses;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Override
    @ManyToOne
    @JoinColumn(name = "turn")
    public ProbeTurn getTurn() {
        return turn;
    }
    public void setTurn(ProbeTurn turn) {
        this.turn = turn;
    }

    @Basic
    @Column(name = "date")
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    @Basic
    @Column(name = "manager")
    public String getManager() {
        return manager;
    }
    public void setManager(String manager) {
        this.manager = manager;
    }

    @Basic
    @Column(name = "organisation")
    public String getOrganisation() {
        return organisation;
    }
    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }

    @OneToOne
    @JoinColumn(name = "analyses")
    public SunAnalyses getAnalyses() {
        return analyses;
    }
    public void setAnalyses(SunAnalyses analyses) {
        this.analyses = analyses;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "SunProbe{\n" +
                "\t\tturn=" + turn.getTurn().getDate().toString() + "\n" +
                "\t\tmanager='" + manager + '\'' + "\n" +
                "\t\torganisation='" + organisation + '\'' + "\n" +
                "\t}";
    }

    @Override
    public JSONObject toJson(int level) {
        JSONObject object = pool.getObject();
        object.put(ID, id);
        object.put(DATE, date.toString());
        object.put(MANAGER, manager);
        object.put(ORGANISATION, organisation);
        object.put(ANALYSES, analyses.toJson());
        return object;
    }
}
