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
    private String title;
    private ActionTime created;
    private long free;
    private long total;
    private boolean archive;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
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
    public long getFree() {
        return free;
    }
    public void setFree(long free) {
        this.free = free;
    }

    @Basic
    @Column(name = "total")
    public long getTotal() {
        return total;
    }
    public void setTotal(long total) {
        this.total = total;
    }

    @Basic
    @Column(name = "archive")
    public boolean isArchive() {
        return archive;
    }
    public void setArchive(boolean archive) {
        this.archive = archive;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * Long.hashCode(free) + hash;
        hash = 31 * Long.hashCode(total) + hash;
        hash = 31 * Boolean.hashCode(archive) + hash;
        return hash;
    }
}
