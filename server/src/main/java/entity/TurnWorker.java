package entity;

import entity.production.Turn;

import javax.persistence.*;

/**
 * Created by szpt_user045 on 05.02.2020.
 */
@Entity
@Table(name = "turn_workers")
public class TurnWorker  {
    private int id;
    private Turn turn;
    private Worker worker;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
