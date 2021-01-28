package entity.storages;

import entity.transport.ActionTime;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

/**
 * Created by szpt_user045 on 06.11.2019.
 */
@Entity
@Table(name = "storage_product_replace")
public class StorageProductReplace {
    private int id;
    private Timestamp time;
    private Set<StorageProductReplaceEntry> entries;
    private ActionTime createTime;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "time")
    public Timestamp getTime() {
        return time;
    }
    public void setTime(Timestamp time) {
        this.time = time;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "replace", cascade = CascadeType.ALL)
    public Set<StorageProductReplaceEntry> getEntries() {
        return entries;
    }
    public void setEntries(Set<StorageProductReplaceEntry> entries) {
        this.entries = entries;
    }

    @OneToOne
    @JoinColumn(name = "created")
    public ActionTime getCreateTime() {
        return createTime;
    }
    public void setCreateTime(ActionTime createTime) {
        this.createTime = createTime;
    }
}
