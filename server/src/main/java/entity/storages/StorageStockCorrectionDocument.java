package entity.storages;

import entity.transport.ActionTime;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

/**
 * Created by szpt_user045 on 03.01.2020.
 */
@Entity
@Table(name = "stock_correction_documents")
public class StorageStockCorrectionDocument {
    private int id;
    private Date date;
    private List<StorageStockCorrection> corrections;
    private ActionTime createTime;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "date")
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "document", cascade = CascadeType.ALL)
    public List<StorageStockCorrection> getCorrections() {
        return corrections;
    }
    public void setCorrections(List<StorageStockCorrection> corrections) {
        this.corrections = corrections;
    }

    @OneToOne
    @JoinColumn(name = "create_time")
    public ActionTime getCreateTime() {
        return createTime;
    }
    public void setCreateTime(ActionTime createTime) {
        this.createTime = createTime;
    }
}
