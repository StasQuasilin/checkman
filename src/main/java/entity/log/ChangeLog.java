package entity.log;

import entity.Worker;
import entity.documents.IDocument;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

/**
 * Created by szpt_user045 on 12.03.2019.
 */
@Entity
@Table(name = "change_log")
public class ChangeLog implements Comparable<ChangeLog>{
    private int id;
    private Timestamp time;
    private String label;
    private String document;
    private Worker creator;
    private Set<Change> changes;

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

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "log", cascade = CascadeType.ALL)
    public Set<Change> getChanges() {
        return changes;
    }
    public void setChanges(Set<Change> changes) {
        this.changes = changes;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * time.hashCode() + hash;
        hash = 31 * document.hashCode() + hash;
        for (Change change : changes) {
            hash = 31 * change.hashCode() + hash;
        }
        return hash;
    }

    @Override
    public int compareTo(ChangeLog o) {
        return time.compareTo(o.time);
    }
}
