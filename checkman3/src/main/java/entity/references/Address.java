package entity.references;

import javax.persistence.*;

@Entity
@Table(name = "addresses")
public class Address {
    private int id;
    private String locality;
    private String street;
    private String build;
    private String flat;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
}
