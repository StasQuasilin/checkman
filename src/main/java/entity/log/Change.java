package entity.log;

import javax.persistence.*;

/**
 * Created by szpt_user045 on 12.03.2019.
 */
@Entity
@Table(name = "changes")
public class Change {
    
    static final String NEW = "new";
    static final String EDIT = "edit";
    static final String DELETE = "delete";
    
    private int id;
    private ChangeLog log;
    private String field;
    private String oldValue;
    private String newValue;

    public Change() {}

    public Change(String field) {
        this.field = field;
    }

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @OneToOne
    @JoinColumn(name = "log")
    public ChangeLog getLog() {
        return log;
    }
    public void setLog(ChangeLog log) {
        this.log = log;
    }

    @Basic
    @Column(name = "field")
    public String getField() {
        return field;
    }
    public void setField(String field) {
        this.field = field;
    }

    @Basic
    @Column(name = "old_value")
    public String getOldValue() {
        return oldValue;
    }
    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    @Basic
    @Column(name = "new_value")
    public String getNewValue() {
        return newValue;
    }
    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }
    
    @Transient
    public String getValue(){
        if (oldValue == null) {
            return NEW;
        } else if (newValue == null) {
            return DELETE;
        } else {
            return EDIT;
        }
    }
}
