package entity;

import org.json.simple.JSONObject;

import javax.persistence.*;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@Entity
@Table(name = "workers")
public class Worker extends JsonAble {
    private int id;
    private Person person;
    private String language;
    private String office;
    private Role role;

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

    @Basic
    @Column(name = "language")
    public String getLanguage() {
        return language;
    }
    public void setLanguage(String language) {
        this.language = language;
    }

    @Basic
    @Column(name = "office")
    public String getOffice() {
        return office;
    }
    public void setOffice(String office) {
        this.office = office;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }

    @Transient
    public String getValue(){
        return person.getValue();
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public JSONObject toShortJson() {
        JSONObject object = pool.getObject();
        object.put(ID, id);
        object.put(PERSON, person.toShortJson());
        return object;
    }

    @Override
    public JSONObject toJson(int level) {
        JSONObject object = toShortJson();
        object.put(ROLE, role.toString());
        object.put(PERSON, person.toJson());
        return object;
    }
}
