package entity.documents;

import entity.DealType;
import entity.Product;
import entity.Worker;
import entity.organisations.Organisation;
import entity.weight.WeightUnit;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@Entity
@Table(name = "deals")
public class DealHash {
    private int id;
    private DealType type;
    private boolean archive;
    private int hash;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Enumerated(EnumType.STRING)
    public DealType getType() {
        return type;
    }
    public void setType(DealType type) {
        this.type = type;
    }

    @Basic
    @Column(name = "archive")
    public boolean isArchive() {
        return archive;
    }
    public void setArchive(boolean archive) {
        this.archive = archive;
    }

    @Basic
    @Column(name = "hash")
    public int getHash() {
        return hash;
    }
    public void setHash(int hash) {
        this.hash = hash;
    }

    @Override
    public int hashCode() {
        return hash;
    }
}
