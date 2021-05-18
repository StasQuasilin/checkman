package entity;

import org.json.simple.JSONObject;

import javax.persistence.*;

/**
 * Created by szpt_user045 on 15.04.2019.
 */
@Entity
@Table(name = "phones")
public class PhoneNumber extends JsonAble{
    private int id;
    private Person person;
    private String number;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "person")
    public Person getPerson() {
        return person;
    }
    public void setPerson(Person person) {
        this.person = person;
    }

    @Basic
    @Column(name = "number")
    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public JSONObject toJson(int level) {
        JSONObject object = pool.getObject();
        object.put(ID, id);
        object.put(NUMBER, number);
        return object;
    }
}
