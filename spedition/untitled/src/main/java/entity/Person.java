package entity;

import org.json.simple.JSONObject;

import javax.persistence.*;
import java.util.Set;

import static constants.Keys.*;

@Entity
@Table(name = "persons")
public class Person extends JsonAble{
    private int id;
    private String surname;
    private String forename;
    private Set<Phone> phones;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
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
    @Column(name = "forename")
    public String getForename() {
        return forename;
    }
    public void setForename(String forename) {
        this.forename = forename;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "person", cascade = CascadeType.ALL)
    public Set<Phone> getPhones() {
        return phones;
    }
    public void setPhones(Set<Phone> phones) {
        this.phones = phones;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = getJsonObject();
        json.put(ID, id);
        json.put(SURNAME, surname);
        json.put(FORENAME, forename);
        return json;
    }

    @Transient
    public String getValue(){
        StringBuilder builder = new StringBuilder();
        builder.append(surname).append(SPACE).append(forename);
        if (phones.size() > 0){
            builder.append(SPACE);
            builder.append(LEFT_BRACKET).append(SPACE);
            int i = 0;
            for (Phone phone : phones){
                builder.append(phone.getNumber());
                if (i < phones.size() - 1){
                    builder.append(COMA).append(SPACE);
                }
                i++;
            }
            builder.append(SPACE).append(RIGHT_BRACKET);
        }

        return builder.toString();
    }
}
