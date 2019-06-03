package entity.laboratory.subdivisions.extraction;

import entity.production.Turn;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

/**
 * Created by szpt_user045 on 04.04.2019.
 */
@Entity
@Table(name = "turns_extraction")
public class ExtractionTurn {
    private int id;
    private Turn turn;
    private List<ExtractionCrude> crudes;
    private Set<StorageProtein> protein;
    private Set<StorageGrease> greases;
    private Set<ExtractionOIl> oils;
    private Set<TurnProtein> turnProteins;
    private Set<TurnGrease> turnGreases;

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

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "turn", cascade = CascadeType.ALL)
    public List<ExtractionCrude> getCrudes() {
        return crudes;
    }
    public void setCrudes(List<ExtractionCrude> crudes) {
        this.crudes = crudes;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "turn", cascade = CascadeType.ALL)
    public Set<StorageProtein> getProtein() {
        return protein;
    }
    public void setProtein(Set<StorageProtein> raws) {
        this.protein = raws;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "turn", cascade = CascadeType.ALL)
    public Set<StorageGrease> getGreases() {
        return greases;
    }
    public void setGreases(Set<StorageGrease> grease) {
        this.greases = grease;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "turn", cascade = CascadeType.ALL)
    public Set<ExtractionOIl> getOils() {
        return oils;
    }
    public void setOils(Set<ExtractionOIl> oil) {
        this.oils = oil;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "turn", cascade = CascadeType.ALL)
    public Set<TurnProtein> getTurnProteins() {
        return turnProteins;
    }
    public void setTurnProteins(Set<TurnProtein> turns) {
        this.turnProteins = turns;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "turn", cascade = CascadeType.ALL)
    public Set<TurnGrease> getTurnGreases() {
        return turnGreases;
    }
    public void setTurnGreases(Set<TurnGrease> turnGreases) {
        this.turnGreases = turnGreases;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * turn.hashCode() + hash;

        for (ExtractionCrude crude : crudes) {
            hash = 31 * crude.hashCode() + hash;
        }

        for (StorageProtein raw : protein) {
            hash = 31 * raw.hashCode() + hash;
        }

        for (StorageGrease grease : greases){
            hash = 31 * grease.hashCode() + hash;
        }

        for (ExtractionOIl oil : oils) {
            hash = 31 * oil.hashCode() + hash;
        }

        for (TurnProtein protein : turnProteins) {
            hash = 31 * protein.hashCode() + hash;
        }

        for (TurnGrease grease : turnGreases) {
            hash = 31 * grease.hashCode() + hash;
        }

        return hash;
    }
}
