package entity.transport;

import javax.persistence.*;

@Entity
@Table(name = "driver_truck_trailer")
public class DriverTruckTrailer {
    private int id;
    private Driver driver;
    private Vehicle truck;
    private Trailer trailer;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "_id")
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @OneToOne
    @JoinColumn(name = "_driver")
    public Driver getDriver() {
        return driver;
    }
    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    @OneToOne
    @JoinColumn(name = "_truck")
    public Vehicle getTruck() {
        return truck;
    }
    public void setTruck(Vehicle truck) {
        this.truck = truck;
    }

    @OneToOne
    @JoinColumn(name = "_trailer")
    public Trailer getTrailer() {
        return trailer;
    }
    public void setTrailer(Trailer trailer) {
        this.trailer = trailer;
    }
}
