package entity.references;

import constants.Keys;
import entity.Person;
import org.json.simple.JSONObject;
import utils.json.JsonAble;

import javax.persistence.*;

@Entity
@Table(name = "drivers")
public class Driver extends JsonAble {
    private int id;
    private Person person;
    private String license;
    private Organisation organisation;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @OneToOne
    @JoinColumn(name = "_person")
    public Person getPerson() {
        return person;
    }
    public void setPerson(Person person) {
        this.person = person;
    }

    @Basic
    @Column(name = "_license")
    public String getLicense() {
        return license;
    }
    public void setLicense(String license) {
        this.license = license;
    }

    @OneToOne
    @JoinColumn(name = "_organisation")
    public Organisation getOrganisation() {
        return organisation;
    }
    public void setOrganisation(Organisation organisation) {
        this.organisation = organisation;
    }

    @Override
    public JSONObject toJson() {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put(Keys.ID, id);
        jsonObject.put(Keys.PERSON, person.toJson());
        jsonObject.put(Keys.LICENSE, license);
        return jsonObject;
    }
}
