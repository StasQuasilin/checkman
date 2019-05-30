package entity.rails;

import entity.transport.Transportation;

import javax.persistence.*;

/**
 * Created by szpt_user045 on 30.05.2019.
 */
@Entity
@Table(name = "truck_transportation")
public class TruckTransportation {
    private int id;
    private Truck truck;
    private Transportation transportation;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @OneToOne
    @JoinColumn(name = "truck")
    public Truck getTruck() {
        return truck;
    }
    public void setTruck(Truck truck) {
        this.truck = truck;
    }

    @OneToOne
    @JoinColumn(name = "transportation")
    public Transportation getTransportation() {
        return transportation;
    }
    public void setTransportation(Transportation transportation) {
        this.transportation = transportation;
    }
}
