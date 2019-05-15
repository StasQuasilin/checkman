package entity.storages;

import javax.persistence.*;

/**
 * Created by szpt_user045 on 15.05.2019.
 */
@Entity
@Table(name = "storages")
public class Storage {
    private int id;
    private String name;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
