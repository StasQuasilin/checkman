package entity.references;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "addresses")
public class Address {
    private int id;
    private String locality;
    private String street;
    private String build;
    private String flat;
}
