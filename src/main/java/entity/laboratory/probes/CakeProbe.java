package entity.laboratory.probes;

import entity.laboratory.CakeAnalyses;
import entity.laboratory.MealAnalyses;

import javax.persistence.*;

/**
 * Created by Kvasik on 10.07.2019.
 */
@Entity
@Table(name = "probe_oilcake")
public class CakeProbe {
    private int id;
    private ProbeTurn turn;
    private String manager;
    private String organisation;
    private CakeAnalyses analyses;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "turn")
    public ProbeTurn getTurn() {
        return turn;
    }
    public void setTurn(ProbeTurn turn) {
        this.turn = turn;
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
    public CakeAnalyses getAnalyses() {
        return analyses;
    }
    public void setAnalyses(CakeAnalyses analyses) {
        this.analyses = analyses;
    }
}
