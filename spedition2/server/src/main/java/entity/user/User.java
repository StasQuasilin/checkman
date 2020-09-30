package entity.user;

import entity.references.Person;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users")
public class User implements Serializable {
    private int id;
    private User supervisor;
    private Role role;
    private Person person;

    @Id
    @Column(name = "_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @OneToOne
    @JoinColumn(name = "_supervisor")
    public User getSupervisor() {
        return supervisor;
    }
    public void setSupervisor(User supervisor) {
        this.supervisor = supervisor;
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
}
