package entity.transportations;

import constants.Keys;
import entity.ActionTime;
import entity.deals.DealType;
import entity.deals.Shipper;
import entity.references.Driver;
import entity.references.Trailer;
import entity.references.Vehicle;
import entity.weight.Weight;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.json.JsonAble;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "transportations")
public class Transportation extends JsonAble {
    private int id;
    private Date date;
    private Driver driver;
    private Vehicle vehicle;
    private Trailer trailer;
    private TransportCustomer customer;
    private Shipper shipper;
    private Weight weight;
    private ActionTime registration;
    private ActionTime timeIn;
    private ActionTime timeOut;
    private Set<TransportationNote> notes = new HashSet<>();
    private Set<TransportationDocument> documents = new HashSet<>();
    private boolean archive;
    private DealType type;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "_date")
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
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
    @JoinColumn(name = "_vehicle")
    public Vehicle getVehicle() {
        return vehicle;
    }
    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    @OneToOne
    @JoinColumn(name = "_trailer")
    public Trailer getTrailer() {
        return trailer;
    }
    public void setTrailer(Trailer trailer) {
        this.trailer = trailer;
    }

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "_customer")
    public TransportCustomer getCustomer() {
        return customer;
    }
    public void setCustomer(TransportCustomer customer) {
        this.customer = customer;
    }

    @OneToOne
    @JoinColumn(name = "_shipper")
    public Shipper getShipper() {
        return shipper;
    }
    public void setShipper(Shipper shipper) {
        this.shipper = shipper;
    }

    @OneToOne
    @JoinColumn(name = "_weight")
    public Weight getWeight() {
        return weight;
    }
    public void setWeight(Weight weight) {
        this.weight = weight;
    }

    @OneToOne
    @JoinColumn(name = "_registration")
    public ActionTime getRegistration() {
        return registration;
    }
    public void setRegistration(ActionTime registration) {
        this.registration = registration;
    }

    @OneToOne
    @JoinColumn(name = "_time_in")
    public ActionTime getTimeIn() {
        return timeIn;
    }
    public void setTimeIn(ActionTime timeIn) {
        this.timeIn = timeIn;
    }

    @OneToOne
    @JoinColumn(name = "_time_out")
    public ActionTime getTimeOut() {
        return timeOut;
    }
    public void setTimeOut(ActionTime timeOut) {
        this.timeOut = timeOut;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "transportation", cascade = CascadeType.ALL)
    public Set<TransportationNote> getNotes() {
        return notes;
    }
    public void setNotes(Set<TransportationNote> notes) {
        this.notes = notes;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "transportation", cascade = CascadeType.ALL)
    public Set<TransportationDocument> getDocuments() {
        return documents;
    }
    public void setDocuments(Set<TransportationDocument> documents) {
        this.documents = documents;
    }

    @Basic
    @Column(name = "_archive")
    public boolean isArchive() {
        return archive;
    }
    public void setArchive(boolean archive) {
        this.archive = archive;
    }

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "_type")
    public DealType getType() {
        return type;
    }
    public void setType(DealType type) {
        this.type = type;
    }

    @Override
    public JSONObject toJson() {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put(Keys.ID, id);
        jsonObject.put(Keys.DATE, date.toString());
        if (driver != null) {
            jsonObject.put(Keys.DRIVER, driver.toJson());
        }
        if(vehicle != null) {
            jsonObject.put(Keys.VEHICLE, vehicle.toJson());
        }
        if (trailer != null){
            jsonObject.put(Keys.TRAILER, trailer.toJson());
        }
        jsonObject.put(Keys.CUSTOMER, customer.toString());
        if (shipper != null){
            jsonObject.put(Keys.SHIPPER, shipper.toJson());
        }
        if (weight != null){
            jsonObject.put(Keys.WEIGHT, weight.toJson());
        }
        if (registration != null){
            jsonObject.put(Keys.REGISTRATION, registration.toJson());
        }
        if (timeIn != null){
            jsonObject.put(Keys.TIME_IN, timeIn.toJson());
        }
        if (timeOut != null){
            jsonObject.put(Keys.TIME_OUT, timeOut.toJson());
        }
        jsonObject.put(Keys.TYPE, type.toString());

        jsonObject.put(Keys.NOTES, notes());
        jsonObject.put(Keys.DOCUMENTS, documents());
        return jsonObject;
    }

    private JSONArray documents() {
        final JSONArray array = new JSONArray();
        for (TransportationDocument document : documents){
            array.add(document.toJson());
        }
        return array;
    }

    private JSONArray notes() {
        final JSONArray array = new JSONArray();
        for (TransportationNote note : notes){
            array.add(note.toJson());
        }
        return array;
    }
}
