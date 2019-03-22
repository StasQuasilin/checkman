package entity;

import javax.persistence.*;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@Entity
@Table(name = "workers")
public class Worker {
    private int id;
    private Person person;

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

    @Transient
    public String getValue(){
        return person.getValue();
    }

    @Override
    public int hashCode() {
        return person.hashCode();
    }
}
