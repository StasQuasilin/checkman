package entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "reports")
public class Report {
    private int id;
    private Timestamp leaveTime;
    private User attendant;
    private Driver driver;
    private Counterparty counterparty;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "leaveTime")
    public Timestamp getLeaveTime() {
        return leaveTime;
    }
    public void setLeaveTime(Timestamp leaveTime) {
        this.leaveTime = leaveTime;
    }

    @OneToOne
    @JoinColumn(name = "attendant")
    public User getAttendant() {
        return attendant;
    }
    public void setAttendant(User attendant) {
        this.attendant = attendant;
    }

    @OneToOne
    @JoinColumn(name = "driver")
    public Driver getDriver() {
        return driver;
    }
    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    @OneToOne
    @JoinColumn(name = "counterpart")
    public Counterparty getCounterparty() {
        return counterparty;
    }
    public void setCounterparty(Counterparty counterparty) {
        this.counterparty = counterparty;
    }
}
