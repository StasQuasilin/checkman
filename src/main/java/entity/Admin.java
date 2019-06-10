package entity;

import javax.persistence.*;

/**
 * Created by szpt_user045 on 10.06.2019.
 */
@Entity
@Table(name = "administration")
public class Admin {
    private int id;
    private Worker worker;
    private boolean duty;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @OneToOne
    @JoinColumn(name = "worker")
    public Worker getWorker() {
        return worker;
    }
    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    @Basic
    @Column(name = "duty")
    public boolean isDuty() {
        return duty;
    }
    public void setDuty(boolean duty) {
        this.duty = duty;
    }
}
