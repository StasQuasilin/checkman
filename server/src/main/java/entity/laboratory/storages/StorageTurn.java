package entity.laboratory.storages;

import entity.laboratory.subdivisions.StorageAnalyse;
import entity.production.Turn;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by szpt_user045 on 06.06.2019.
 */
@Entity
@Table(name = "storage_turns")
public class StorageTurn {
    private int id;
    private Turn turn;
    private Set<StorageAnalyses> analyses;

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
    public Set<StorageAnalyses> getAnalyses() {
        return analyses;
    }
    public void setAnalyses(Set<StorageAnalyses> analyses) {
        this.analyses = analyses;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * turn.getId() + hash;
        for (StorageAnalyses analyse : analyses){
            hash = 31 * analyse.hashCode() + hash;
        }
        return hash;
    }
}
