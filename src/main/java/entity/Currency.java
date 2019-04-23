package entity;

import javax.persistence.*;

/**
 * Created by quasilin on 23.04.2019.
 */
@Entity
@Table(name = "currency")
public class Currency {
    private int id;
    private int name;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public int getName() {
        return name;
    }
    public void setName(int name) {
        this.name = name;
    }
}
