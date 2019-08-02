package entity.production;

import javax.persistence.*;

/**
 * Created by szpt_user045 on 10.04.2019.
 */
@Entity
@Table(name = "forpress")
public class Forpress {
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
    @Column(name = "name")
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
