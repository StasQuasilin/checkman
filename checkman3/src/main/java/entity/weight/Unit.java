package entity.weight;

import javax.persistence.*;

@Entity
@Table(name = "weight_units")
public class Unit {
    private int id;
    private String name;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "_name")
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
