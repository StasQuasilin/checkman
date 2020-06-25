package entity;

import org.json.simple.JSONObject;

import javax.persistence.*;

import static constants.Keys.ID;

@Entity
@Table(name = "drivers")
public class Driver extends JsonAble{
    private int id;
    private String uuid;
    private Person person;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "uuid")
    public String getUuid() {
        return uuid;
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @OneToOne
    @JoinColumn(name = "person")
    public Person getPerson() {
        return person;
    }
    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public JSONObject toJson() {
        final JSONObject jsonObject = person.toJson();
        jsonObject.put(ID, id);
        return jsonObject;
    }
}
