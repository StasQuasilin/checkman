package entity.laboratory.subdivisions.extraction;

import entity.Worker;
import entity.transport.ActionTime;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by szpt_user045 on 09.04.2019.
 */
@Entity
@Table(name = "extraction_raw")
public class ExtractionRaw {
    private int id;
    private ExtractionTurn turn;
    private Timestamp time;
    private float protein;
    private float cellulose;
    private ActionTime createTime;
    private Worker creator;

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
    public ExtractionTurn getTurn() {
        return turn;
    }
    public void setTurn(ExtractionTurn turn) {
        this.turn = turn;
    }

    @Basic
    @Column(name = "time")
    public Timestamp getTime() {
        return time;
    }
    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Basic
    @Column(name = "protein")
    public float getProtein() {
        return protein;
    }
    public void setProtein(float protein) {
        this.protein = protein;
    }

    @Basic
    @Column(name = "cellulose")
    public float getCellulose() {
        return cellulose;
    }
    public void setCellulose(float cellulose) {
        this.cellulose = cellulose;
    }

    @OneToOne
    @JoinColumn(name = "create_time")
    public ActionTime getCreateTime() {
        return createTime;
    }
    public void setCreateTime(ActionTime createTime) {
        this.createTime = createTime;
    }

    @OneToOne
    @JoinColumn(name = "creator")
    public Worker getCreator() {
        return creator;
    }
    public void setCreator(Worker creator) {
        this.creator = creator;
    }

    @Override
    public int hashCode() {
        int hash = 7;
//        private Timestamp time;
        hash = 31 * time.hashCode() + hash;
//        private float protein;
        hash = 31 * Float.hashCode(protein) + hash;
//        private float cellulose;
        hash = 31 * Float.hashCode(cellulose) + hash;
//        private ActionTime createTime;
        hash = 31 * createTime.hashCode() + hash;
//        private Worker creator;
        hash = 31 * creator.hashCode() + hash;
        return hash;
    }
}
