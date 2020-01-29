package entity.transport;

import entity.DealType;
import entity.JsonAble;
import entity.Worker;
import entity.documents.Shipper;
import entity.laboratory.MealAnalyses;
import entity.laboratory.OilAnalyses;
import entity.laboratory.SunAnalyses;
import entity.organisations.Organisation;
import entity.products.Product;
import entity.weight.Weight;
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
    private Set<TransportStorageUsed> usedStorages = new HashSet<>();
    private boolean archive;
    private boolean done;
    private String uid;

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
//    @ManyToMany
//    @JoinTable(name="transportation_notes", joinColumns = @JoinColumn(name="document", referencedColumnName="uid"))
    public List<DocumentNote> getNotes() {
        return notes;
    }
    public void setNotes(List<DocumentNote> notes) {
        this.notes = notes;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "transportation", cascade = CascadeType.ALL)
    public Set<TransportStorageUsed> getUsedStorages() {
        return usedStorages;
    }
    public void setUsedStorages(Set<TransportStorageUsed> usedStorages) {
        this.usedStorages = usedStorages;
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

    @Override
    public JSONObject toJson() {
        JSONObject object = pool.getObject();
        object.put(ID, id);
        if (driver != null) {
            object.put(DRIVER, driver.toJson());
        } else {
            object.put(DRIVER, EMPTY_OBJECT);
        }
        if (vehicle != null) {
            object.put(VEHICLE, vehicle.toJson());
        }else {
            object.put(VEHICLE, EMPTY_OBJECT);
        }
        if (trailer != null){
            object.put(TRAILER, trailer.toJson());
        } else {
            object.put(TRAILER, EMPTY_OBJECT);
        }
        if (transporter != null) {
            object.put(TRANSPORTER, transporter.toJson());
        }else {
            object.put(TRANSPORTER, EMPTY_OBJECT);
        }
        object.put(NOTES, notes.stream().map(DocumentNote::toJson).collect(Collectors.toList()));
        return object;
    }
}
