package entity.organisations;

import javax.persistence.*;

/**
 * Created by quasilin on 11.03.2019.
 */
@Entity
@Table(name = "organisation_types")
public class OrganisationType {
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
