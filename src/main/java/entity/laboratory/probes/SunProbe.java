package entity.laboratory.probes;

import entity.Worker;
import entity.laboratory.SunAnalyses;
import entity.production.Turn;

import javax.persistence.*;

/**
 * Created by quasilin on 01.04.2019.
 */
@Entity
@Table(name = "probe_sun")
public class SunProbe {
    private int id;
    private Turn turn;
    private Worker manager;
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

    @OneToOne
    @JoinColumn(name = "turn")
    public Turn getTurn() {
        return turn;
    }
    public void setTurn(Turn turn) {
        this.turn = turn;
    }

    @OneToOne
    @JoinColumn(name = "role.manager")
    public Worker getManager() {
        return manager;
    }
    public void setManager(Worker manager) {
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
        int hash = 7;
        if (manager != null) {
            hash = 31 * manager.hashCode() + hash;
        }
        if (organisation != null) {
            hash = 31 * organisation.hashCode() + hash;
        }

        hash = 31 * analyses.hashCode() + hash;

        return hash;
    }
}
