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
    private int free;
    private boolean archive;

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

    @Basic
    @Column(name = "free")
    public int getFree() {
        return free;
    }
    public void setFree(int hash) {
        this.free = hash;
    }

    @Basic
    @Column(name = "archive")
    public boolean isArchive() {
        return archive;
    }
    public void setArchive(boolean archive) {
        this.archive = archive;
    }
}
