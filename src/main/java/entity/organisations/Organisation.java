package entity.organisations;

import javax.persistence.*;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@Entity
@Table(name = "organisations")
public class Organisation {

    private int id;
    private OrganisationType type;
    private String name;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @OneToOne
    @JoinColumn(name = "type")
    public OrganisationType getType() {
        return type;
    }
    public void setType(OrganisationType type) {
        this.type = type;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Transient
    public String getFullName() {
        return name + (type != null ? ", " + type.getName() : "");
    }
}
