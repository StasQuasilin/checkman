package entity.seals;

import entity.transport.ActionTime;

import javax.persistence.*;

/**
 * Created by quasilin on 07.04.2019.
 */
@Entity
@Table(name = "seals_batch")
public class SealBatch {
    private int id;
    private ActionTime created;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @OneToOne
    @JoinColumn(name = "created")
    public ActionTime getCreated() {
        return created;
    }
    public void setCreated(ActionTime created) {
        this.created = created;
    }
}
