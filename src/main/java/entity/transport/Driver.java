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
@Table(name = "drivers")
public class Driver extends JsonAble{
    private int id;
    private Person person;
    private Organisation organisation;
    private Vehicle vehicle;
    private String license;
    private boolean approved;

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

    @OneToOne
    @JoinColumn(name = "vehicle")
    public Vehicle getVehicle() {
        return vehicle;
    }
    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    @Basic
    @Column(name = "license")
    public String getLicense() {
        return license;
    }
    public void setLicense(String license) {
        this.license = license;
    }

    @Basic
    @Column(name = "approved")
    public boolean isApproved() {
        return approved;
    }
    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return getPerson().getValue();
    }

    @Override
    public JSONObject toJson(int level) {
        JSONObject json = person.toJson();
        json.put(ID, id);
        json.put(PERSON, person.toJson());
        json.put(LICENSE, license);
        if (vehicle != null){
            json.put(VEHICLE, vehicle.toJson());
        }
        if (organisation != null){
            json.put(ORGANISATION, organisation.toJson());
        }
        json.put(VALUE, person.getValue());
        return json;
    }
}
