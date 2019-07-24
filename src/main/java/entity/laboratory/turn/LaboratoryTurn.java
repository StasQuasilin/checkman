package entity.laboratory.turn;

import entity.Worker;
import entity.production.Turn;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by szpt_user045 on 03.06.2019.
 */
@Entity
@Table(name = "turns_laboratory")
public class LaboratoryTurn {
    private int id;
    private Turn turn;
    private Set<LaboratoryTurnWorker> workers = new HashSet<>();

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
    public Set<LaboratoryTurnWorker> getWorkers() {
        return workers;
    }
    public void setWorkers(Set<LaboratoryTurnWorker> workers) {
        this.workers = workers;
    }
}
