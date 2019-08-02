package entity;

import utils.ArchiveType;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by szpt_user045 on 17.07.2019.
 */
@Entity
@Table(name= "archive_data")
public class ArchiveData {
    private int id;
    private ArchiveType type;
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
    public ArchiveType getType() {
        return type;
    }
    public void setType(ArchiveType type) {
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
