package entity.transport;

import entity.organisations.Counterparty;
import entity.Person;

import javax.persistence.*;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@Entity
@Table(name = "_drivers")
public class Driver {
    private int id;
    private Person person;
    private Counterparty organisation;
    private Truck truck;
    private String license;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
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
    public Counterparty getOrganisation() {
        return organisation;
    }
    public void setOrganisation(Counterparty organisation) {
        this.organisation = organisation;
    }

    @OneToOne
    @JoinColumn(name = "truck")
    public Truck getTruck() {
        return truck;
    }
    public void setTruck(Truck truck) {
        this.truck = truck;
    }


    @Basic
    @Column(name = "license")
    public String getLicense() {
        return license;
    }
    public void setLicense(String license) {
        this.license = license;
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

    @Override
    public String toString() {
        return getPerson().getValue();
    }
}
