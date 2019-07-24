package entity.laboratory.turn;

import entity.Worker;

import javax.persistence.*;

/**
 * Created by szpt_user045 on 23.07.2019.
 */
@Entity
@Table(name = "laboratory_worker")
public class LaboratoryTurnWorker {
    private int id;
    private LaboratoryTurn turn;
    private Worker worker;

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
    public LaboratoryTurn getTurn() {
        return turn;
    }
    public void setTurn(LaboratoryTurn turn) {
        this.turn = turn;
    }

    @OneToOne
    @JoinColumn(name = "worker")
    public Worker getWorker() {
        return worker;
    }
    public void setWorker(Worker worker) {
        this.worker = worker;
    }
}
