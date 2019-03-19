package entity.transport;

import entity.organisations.Organisation;
import entity.Person;

import javax.persistence.*;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@Entity
@Table(name = "drivers")
public class Driver {
    private int id;
    private Person person;
    private Organisation organisation;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @OneToOne
    @JoinColumn(name = "person")
    public Person getPerson() {
        return person;
    }
    public void setPerson(Person person) {
        this.person = person;
    }

    @OneToOne
    @JoinColumn(name = "organisation")
    public Organisation getOrganisation() {
        return organisation;
    }

    public void setOrganisation(Organisation organisation) {
        this.organisation = organisation;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * person.hashCode() + hash;
        if (organisation != null) {
            hash = 31 * organisation.hashCode() + hash;
        }

        return hash;
    }
}
