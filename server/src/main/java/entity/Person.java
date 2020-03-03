package entity;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.U;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@Entity
@Table(name = "persons")
public class Person extends JsonAble{
    private int id;
    private String forename;
    private String surname;
    private String patronymic;
    private Set<PhoneNumber> phones;

    public Person() {
        phones = new HashSet<>();
    }

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "forename")
    public String getForename() {
        return forename;
    }
    public void setForename(String forename) {
        this.forename = forename;
    }

    @Basic
    @Column(name = "surname")
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Basic
    @Column(name = "patronymic")
    public String getPatronymic() {
        return patronymic;
    }
    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "person")
    public Set<PhoneNumber> getPhones() {
        return phones;
    }
    public void setPhones(Set<PhoneNumber> phones) {
        this.phones = phones;
    }

    @Transient
    public String getValue() {
        return
                surname +
                        (U.exist(forename) ? " " + forename.substring(0, 1) + "." : "") +
                        (U.exist(patronymic) ? " " + patronymic.substring(0, 1) + "." : "");
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        return getClass() == obj.getClass() && hashCode() == obj.hashCode();
    }

    @Transient
    public String getAccost() {
        return forename + " " + patronymic;
    }

    @Override
    public JSONObject toShortJson() {
        JSONObject json = pool.getObject();
        json.put(ID, id);
        json.put(VALUE, getValue());
        json.put(PHONES, phones.stream().map(PhoneNumber::toJson).collect(Collectors.toList()));
        return json;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = toShortJson();

        json.put(SURNAME, surname);
        json.put(FORENAME, forename);
        json.put(PATRONYMIC, patronymic);

        return json;
    }
}
