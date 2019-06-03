package entity.laboratory;

import entity.Worker;
import entity.production.Turn;

import javax.persistence.*;

/**
 * Created by szpt_user045 on 03.06.2019.
 */
@Entity
@Table(name = "turns_laboratory")
public class LaboratoryTurn {
    private int id;
    private Turn turn;
    private Worker worker;

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
    @JoinColumn(name = "worker")
    public Worker getWorker() {
        return worker;
    }
    public void setWorker(Worker worker) {
        this.worker = worker;
    }
}
