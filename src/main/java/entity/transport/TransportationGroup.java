package entity.transport;

import entity.organisations.Address;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by szpt_user045 on 03.03.2020.
 */
@Entity
@Table(name = "transportation_groups")
public class TransportationGroup {
    private int id;
    private Transportation transportation;
    private Address address;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "transportation")
    public Transportation getTransportation() {
        return transportation;
    }
    public void setTransportation(Transportation transportation) {
        this.transportation = transportation;
    }

    @OneToOne
    @JoinColumn(name = "address")
    public Address getAddress() {
        return address;
    }
    public void setAddress(Address address) {
        this.address = address;
    }
}
