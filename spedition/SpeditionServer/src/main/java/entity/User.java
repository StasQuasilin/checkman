package entity;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {
    private int id;
    private Person person;
    private Role role;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "role")
    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }
}
