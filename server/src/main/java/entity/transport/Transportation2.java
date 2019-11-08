package entity.transport;

import entity.DealType;
import entity.Worker;
import entity.documents.Shipper;
import entity.organisations.Counterparty;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@Entity
@Table(name = "_transportations")
public class Transportation2 {
    private int id;
    private DealType type;
    private Date date;
    private Driver driver;
    private String driverLicense;
    private Truck truck;
    private String truckNumber;
    private Trailer trailer;
    private String trailerNumber;
    private Counterparty transporter;
    private Shipper shipper;
    private TransportCustomer customer;
    private ActionTime registered;
    private ActionTime timeIn;
    private ActionTime timeOut;
    private Set<TransportationProduct> products = new HashSet<>();
    private Set<TransportationNote> notes = new HashSet<>();
    private boolean done;
    private boolean archive;
    private ActionTime createTime;
    private Worker manager;
    private String uid;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "date")
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    public DealType getType() {
        return type;
    }
    public void setType(DealType type) {
        this.type = type;
    }

    @OneToOne
    @JoinColumn(name = "driver")
    public Driver getDriver() {
        return driver;
    }
    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    @Basic
    @Column(name = "driver_license")
    public String getDriverLicense() {
        return driverLicense;
    }
    public void setDriverLicense(String driverLicense) {
        this.driverLicense = driverLicense;
    }

    @OneToOne
    @JoinColumn(name = "truck")
    public Truck getTruck() {
        return truck;
    }
    public void setTruck(Truck truck) {
        this.truck = truck;
    }

    @Basic
    @Column(name = "truck_number")
    public String getTruckNumber() {
        return truckNumber;
    }
    public void setTruckNumber(String truckNumber) {
        this.truckNumber = truckNumber;
    }

    @OneToOne
    @JoinColumn(name = "trailer")
    public Trailer getTrailer() {
        return trailer;
    }
    public void setTrailer(Trailer trailer) {
        this.trailer = trailer;
    }

    @Basic
    @Column(name = "trailer_number")
    public String getTrailerNumber() {
        return trailerNumber;
    }
    public void setTrailerNumber(String trailerNumber) {
        this.trailerNumber = trailerNumber;
    }

    @OneToOne
    @JoinColumn(name = "transporter")
    public Counterparty getTransporter() {
        return transporter;
    }
    public void setTransporter(Counterparty transporter) {
        this.transporter = transporter;
    }

    @OneToOne
    @JoinColumn(name = "shipper")
    public Shipper getShipper() {
        return shipper;
    }
    public void setShipper(Shipper shipper) {
        this.shipper = shipper;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "customer")
    public TransportCustomer getCustomer() {
        return customer;
    }
    public void setCustomer(TransportCustomer customer) {
        this.customer = customer;
    }

    @OneToOne
    @JoinColumn(name = "registered")
    public ActionTime getRegistered() {
        return registered;
    }
    public void setRegistered(ActionTime timeRegistration) {
        this.registered = timeRegistration;
    }

    @OneToOne
    @JoinColumn(name = "time_in")
    public ActionTime getTimeIn() {
        return timeIn;
    }
    public void setTimeIn(ActionTime timeIn) {
        this.timeIn = timeIn;
    }

    @OneToOne
    @JoinColumn(name = "time_out")
    public ActionTime getTimeOut() {
        return timeOut;
    }
    public void setTimeOut(ActionTime timeOut) {
        this.timeOut = timeOut;
    }

    @OneToOne
    @JoinColumn(name = "create_time")
    public ActionTime getCreateTime() {
        return createTime;
    }
    public void setCreateTime(ActionTime createTime) {
        this.createTime = createTime;
    }

    @OneToOne
    @JoinColumn(name = "manager")
    public Worker getManager() {
        return manager;
    }
    public void setManager(Worker manager) {
        this.manager = manager;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "transportation", cascade = CascadeType.ALL)
    public Set<TransportationNote> getNotes() {
        return notes;
    }
    public void setNotes(Set<TransportationNote> notes) {
        this.notes = notes;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "transportation", cascade = CascadeType.ALL)
    public Set<TransportationProduct> getProducts() {
        return products;
    }
    public void setProducts(Set<TransportationProduct> products) {
        this.products = products;
    }

    @Basic
    @JoinColumn(name = "done")
    public boolean isDone() {
        return done;
    }
    public void setDone(boolean done) {
        this.done = done;
    }

    @Basic
    @Column(name = "archive")
    public boolean isArchive() {
        return archive;
    }
    public void setArchive(boolean archive) {
        this.archive = archive;
    }

    @Transient
    public boolean anyAction(){
        return
            timeIn != null ||
            timeOut != null;
    }

    @Override
    public int hashCode() {

        return id;
    }

    @Basic
    @Column(name = "uid")
    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }
}
