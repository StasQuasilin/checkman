package entity.weight;

import org.glassfish.jersey.server.BackgroundScheduler;

import javax.persistence.*;

/**
 * Created by szpt_user045 on 19.03.2019.
 */
@Entity
@Table(name = "weight_units")
public class WeightUnit {
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
}
