package entity.laboratory.storages;

import entity.laboratory.OilAnalyses;
import entity.storages.Storage;
import entity.transport.ActionTime;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by szpt_user045 on 06.06.2019.
 */
@Entity
@Table(name = "storage_analyses")
public class StorageAnalyses {
    private int id;
    private StorageTurn turn;
    private Timestamp date;
    private Storage storage;
    private OilAnalyses oilAnalyses;

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
    public StorageTurn getTurn() {
        return turn;
    }
    public void setTurn(StorageTurn turn) {
        this.turn = turn;
    }

    @Basic
    @Column(name = "date")
    public Timestamp getDate() {
        return date;
    }
    public void setDate(Timestamp date) {
        this.date = date;
    }

    @OneToOne
    @JoinColumn(name = "storage")
    public Storage getStorage() {
        return storage;
    }
    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    @OneToOne
    @JoinColumn(name = "oil")
    public OilAnalyses getOilAnalyses() {
        return oilAnalyses;
    }
    public void setOilAnalyses(OilAnalyses oilAnalyses) {
        this.oilAnalyses = oilAnalyses;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * storage.getId() + hash;
        hash = 31 * oilAnalyses.hashCode() + hash;
        return hash;
    }
}
