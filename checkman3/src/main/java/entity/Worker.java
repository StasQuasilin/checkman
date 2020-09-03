package entity;

import com.sun.xml.bind.v2.model.core.ID;
import org.json.simple.JSONObject;
import utils.json.JsonAble;

import javax.persistence.*;

import static constants.Keys.*;

@Entity
@Table(name = "workers")
public class Worker extends JsonAble {
    private int id;
    private Role role;
    private Person person;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "_role")
    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }

    @OneToOne
    @JoinColumn(name = "_person")
    public Person getPerson() {
        return person;
    }
    public void setPerson(Person person) {
        this.person = person;
    }


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put(ID, id);
        json.put(ROLE, role.toString());
        json.put(PERSON, person.toJson());
        return json;
    }
}
