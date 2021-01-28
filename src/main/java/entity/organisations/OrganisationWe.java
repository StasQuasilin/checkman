package entity.organisations;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "organisation_we")
public class OrganisationWe implements Serializable {

    private Organisation organisation;

    @Id
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="_organisation")
    public Organisation getOrganisation() {
        return organisation;
    }
    public void setOrganisation(Organisation organisation) {
        this.organisation = organisation;
    }

    @Override
    public int hashCode() {
        return organisation.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return getClass().equals(obj.getClass()) && hashCode() == obj.hashCode();
    }
}
