package entity;

import utils.ArchivatorType;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by szpt_user045 on 17.07.2019.
 */
@Entity
@Table(name= "archivator_data")
public class ArchivatorData {
    private int id;
    private ArchivatorType type;
    private int document;
    private Timestamp time;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    public ArchivatorType getType() {
        return type;
    }
    public void setType(ArchivatorType type) {
        this.type = type;
    }

    @Basic
    @Column(name = "document")
    public int getDocument() {
        return document;
    }
    public void setDocument(int document) {
        this.document = document;
    }

    @Basic
    @Column(name = "_time")
    public Timestamp getTime() {
        return time;
    }
    public void setTime(Timestamp time) {
        this.time = time;
    }
}
