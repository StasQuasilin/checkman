package entity.transport;

import entity.JsonAble;
import entity.organisations.Organisation;
import entity.Person;
import org.json.simple.JSONObject;

import javax.persistence.*;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@Entity
@Table(name = "_drivers")
public class Driver extends JsonAble {
    private int id;
    private Person person;
    private Organisation organisation;
    private Truck truck;
    private String license;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Override
    public JSONObject toJson() {
        JSONObject json = pool.getObject();
        json.put(ID, id);
        json.put(VALUE, person.getValue());
        json.put(LICENSE, license);
        if (truck != null) {
            json.put(TRUCK, truck.toJson());
        }
        return json;
    }
}
