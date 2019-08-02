package entity;

import javax.persistence.*;

/**
 * Created by szpt_user045 on 15.04.2019.
 */
@Entity
@Table(name = "phones")
public class PhoneNumber {
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
        return number.hashCode();
    }
}
