package bot;

import entity.Worker;

import javax.lang.model.element.Name;
import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Created by szpt_user045 on 16.04.2019.
 */
@Entity
@Table(name = "bot_uids")
public class BotUID {
    private int id;
    private String uid;
    private Worker worker;
    private Timestamp term;

    static final int lifeTime = 5;

    public BotUID() {}
    public BotUID(String uid, Worker worker) {
        this.uid = uid;
        this.worker = worker;
        term = Timestamp.valueOf(LocalDateTime.now().plusMinutes(lifeTime));
    }

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name="uid")
    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
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
    @Column(name = "term")
    public Timestamp getTerm() {
        return term;
    }
    public void setTerm(Timestamp term) {
        this.term = term;
    }

    @Transient
    public boolean isOld(){
        return term.toLocalDateTime().isBefore(LocalDateTime.now());
    }
}
