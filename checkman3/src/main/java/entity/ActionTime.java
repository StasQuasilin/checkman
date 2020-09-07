package entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "action_times")
public class ActionTime {
    private int id;
    private Timestamp time;
    private Worker worker;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "_time")
    public Timestamp getTime() {
        return time;
    }
    public void setTime(Timestamp time) {
        this.time = time;
    }

    @OneToOne
    @JoinColumn(name = "_worker")
    public Worker getWorker() {
        return worker;
    }
    public void setWorker(Worker worker) {
        this.worker = worker;
    }
}
