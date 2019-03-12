package entity.log;

import entity.Worker;
import entity.documents.IDocument;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by szpt_user045 on 12.03.2019.
 */
@Entity
@Table(name = "change_log")
public class ChangeLog {
    private int id;
    private Timestamp time;
    private String label;
    private String document;
    private Worker creator;

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

    @Basic
    @Column(name = "label")
    public String getLabel() {
        return label;
    }
    public void setLabel(String label) {
        this.label = label;
    }

    @Basic
    @Column(name = "document")
    public String getDocument() {
        return document;
    }
    public void setDocument(String document) {
        this.document = document;
    }

    @OneToOne
    @JoinColumn(name = "creator")
    public Worker getCreator() {
        return creator;
    }
    public void setCreator(Worker creator) {
        this.creator = creator;
    }
}
