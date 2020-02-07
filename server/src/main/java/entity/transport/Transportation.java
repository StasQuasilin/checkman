package entity.transport;

import entity.DealType;
import entity.JsonAble;
import entity.Worker;
import entity.documents.Shipper;
import entity.laboratory.MealAnalyses;
import entity.laboratory.OilAnalyses;
import entity.laboratory.SunAnalyses;
import entity.organisations.Address;
import entity.organisations.Organisation;
import entity.products.Product;
import entity.weight.Weight;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@Entity
@Table(name = "transportations")
public class Transportation extends JsonAble implements Serializable {
    private int id;
    private Date date;
    private Organisation counterparty;
    private Address address;
    private int deal;
    private DealType type;
    private Vehicle vehicle;
    private Trailer trailer;
    private String truckNumber;
    private String trailerNumber;
    private Organisation transporter;
    private String transporterValue;
    private Driver driver;
    private String driverLicense;
    private Shipper shipper;
    private ActionTime timeRegistration;
    private ActionTime timeIn;
    private ActionTime timeOut;
    private Product product;
    private Weight weight;
    private SunAnalyses sunAnalyses;
    private OilAnalyses oilAnalyses;
    private MealAnalyses mealAnalyses;
    private Worker creator;
    private Worker manager;
    private List<DocumentNote> notes = new ArrayList<>();
    private boolean archive;
    private boolean done;
    private String uid;
    private ActionTime createTime;
    private TransportCustomer customer;
    private float amount;

    @Id
    @GeneratedValue
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

    @OneToOne
    @JoinColumn(name = "counterparty")
    public Organisation getCounterparty() {
        return counterparty;
    }
    public void setCounterparty(Organisation organisation) {
        this.counterparty = organisation;
    }

    @OneToOne
    @JoinColumn(name = "address")
    public Address getAddress() {
        return address;
    }
    public void setAddress(Address address) {
        this.address = address;
    }

    @Basic
    @Column(name = "deal")
    public int getDeal() {
        return deal;
    }
    public void setDeal(int deal) {
        this.deal = deal;
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
    @JoinColumn(name = "vehicle")
    public Vehicle getVehicle() {
        return vehicle;
    }
    void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    @OneToOne
    @JoinColumn(name = "trailer")
    public Trailer getTrailer() {
        return trailer;
    }
    void setTrailer(Trailer trailer) {
        this.trailer = trailer;
    }

    @Basic
    @Column(name = "truck_number")
    public String getTruckNumber() {
        return truckNumber;
    }
    void setTruckNumber(String truckNumber) {
        this.truckNumber = truckNumber;
    }

    @Basic
    @Column(name = "trailer_number")
    public String getTrailerNumber() {
        return trailerNumber;
    }
    void setTrailerNumber(String trailerNumber) {
        this.trailerNumber = trailerNumber;
    }

    @OneToOne
    @JoinColumn(name = "organisation_transporter")
    public Organisation getTransporter() {
        return transporter;
    }
    void setTransporter(Organisation transporter) {
        this.transporter = transporter;
    }

    @Basic
    @Column(name = "transporter")
    public String getTransporterValue() {
        return transporterValue;
    }
    void setTransporterValue(String transporterValue) {
        this.transporterValue = transporterValue;
    }

    @OneToOne
    @JoinColumn(name = "driver")
    public Driver getDriver() {
        return driver;
    }
    void setDriver(Driver driver) {
        this.driver = driver;
    }

    @Basic
    @Column(name = "driver_license")
    public String getDriverLicense() {
        return driverLicense;
    }
    void setDriverLicense(String driverLicense) {
        this.driverLicense = driverLicense;
    }

    @OneToOne
    @JoinColumn(name = "shipper")
    public Shipper getShipper() {
        return shipper;
    }
    public void setShipper(Shipper shipper) {
        this.shipper = shipper;
    }

    @OneToOne
    @JoinColumn(name = "time_registration")
    public ActionTime getTimeRegistration() {
        return timeRegistration;
    }
    public void setTimeRegistration(ActionTime timeRegistration) {
        this.timeRegistration = timeRegistration;
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
    @JoinColumn(name = "product")
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }

    @OneToOne
    @JoinColumn(name = "weight")
    public Weight getWeight() {
        return weight;
    }
    public void setWeight(Weight weight) {
        this.weight = weight;
    }

    @OneToOne
    @JoinColumn(name = "sun_analyses")
    public SunAnalyses getSunAnalyses() {
        return sunAnalyses;
    }
    public void setSunAnalyses(SunAnalyses sunAnalyse) {
        this.sunAnalyses = sunAnalyse;
    }

    @OneToOne
    @JoinColumn(name = "oil_analyses")
    public OilAnalyses getOilAnalyses() {
        return oilAnalyses;
    }
    public void setOilAnalyses(OilAnalyses oilAnalyses) {
        this.oilAnalyses = oilAnalyses;
    }

    @OneToOne
    @JoinColumn(name = "meal_analyses")
    public MealAnalyses getMealAnalyses() {
        return mealAnalyses;
    }
    public void setMealAnalyses(MealAnalyses mealAnalyses) {
        this.mealAnalyses = mealAnalyses;
    }

    @OneToOne
    @JoinColumn(name = "creator")
    public Worker getCreator() {
        return creator;
    }
    public void setCreator(Worker creator) {
        this.creator = creator;
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
    public List<DocumentNote> getNotes() {
        return notes;
    }
    public void setNotes(List<DocumentNote> notes) {
        this.notes = notes;
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
    public boolean any(){
        return
            timeIn != null ||
            timeOut != null ||
            weight != null ||
            sunAnalyses != null ||
            oilAnalyses != null ||
            mealAnalyses != null;
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

    @OneToOne
    @JoinColumn(name = "create_time")
    public ActionTime getCreateTime() {
        return createTime;
    }
    public void setCreateTime(ActionTime createTime) {
        this.createTime = createTime;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "customer")
    public TransportCustomer getCustomer() {
        return customer;
    }
    public void setCustomer(TransportCustomer customer) {
        this.customer = customer;
    }

    @Basic
    @Column(name = "amount")
    public float getAmount() {
        return amount;
    }
    public void setAmount(float amount) {
        this.amount = amount;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = pool.getObject();

        json.put(ID, id);
        json.put(TYPE, type.toString());
        json.put(DATE, date.toString());
        json.put(PRODUCT, product.toJson());
        json.put(CUSTOMER, customer.toString());
        json.put(PLAN, amount);
        json.put(COUNTERPARTY, counterparty.toJson());
        json.put(SHIPPER, shipper.getValue());
        if (address != null){
            json.put(ADDRESS, address.toJson());
        }
        if (driver != null) {
            json.put(DRIVER, driver.toJson());
        } else {
            json.put(DRIVER, EMPTY_OBJECT);
        }
        json.put(LICENSE, driverLicense);
        if (vehicle != null) {
            json.put(VEHICLE, vehicle.toJson());
        }else {
            json.put(VEHICLE, EMPTY_OBJECT);
        }
        if (trailer != null){
            json.put(TRAILER, trailer.toJson());
        } else {
            json.put(TRAILER, EMPTY_OBJECT);
        }
        if (transporter != null) {
            json.put(TRANSPORTER, transporter.toJson());
        }else {
            json.put(TRANSPORTER, EMPTY_OBJECT);
        }
        if (timeRegistration != null) {
            json.put(REGISTRATION, timeRegistration.toJson());
        }
        if (timeIn != null) {
            json.put(TIME_IN, timeIn.toJson());
        }
        if (timeOut != null) {
            json.put(TIME_OUT, timeOut.toJson());
        }
        if (weight != null) {
            json.put(WEIGHT, weight.toJson());
        }
        json.put(ANALYSES, analyses());
        json.put(NOTES, notes.stream().map(DocumentNote::toJson).collect(Collectors.toList()));
        json.put(ANY, any());
        json.put(ARCHIVE, archive);
        json.put(DONE, done);
        json.put(MANAGER, manager.toJson());
        json.put(CREATE, createTime.toJson());
        return json;
    }

    private JSONObject analyses() {
        JSONObject json = pool.getObject();
        if (sunAnalyses != null) {
            json.put(SUN, sunAnalyses.toJson());
        }
        if (oilAnalyses != null) {
            json.put(OIL, oilAnalyses.toJson());
        }
        return json;
    }
}
