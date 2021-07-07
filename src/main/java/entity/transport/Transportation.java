package entity.transport;

import api.sockets.handlers.OnSubscribeHandler;
import constants.Constants;
import entity.DealType;
import entity.JsonAble;
import entity.Worker;
import entity.documents.Deal;
import entity.documents.DealProduct;
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
import utils.NoteUtil;
import utils.U;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@Entity
@Table(name = Constants.TRANSPORTATIONS)
public class Transportation extends JsonAble implements Serializable, Constants {
    private int id;
    private Date date;
    private Vehicle vehicle;
    private Trailer trailer;
    private String truckNumber;
    private String trailerNumber;
    private Organisation transporter;
    private String transporterValue;
    private Driver driver;
    private String driverLicense;
    private ActionTime timeRegistration;
    private ActionTime timeIn;
    private ActionTime timeOut;
    private Worker manager;
    private List<DocumentNote> notes = new ArrayList<>();
    private boolean archive;
    private boolean done;
    private String uid;
    private TransportCustomer customer;
    private ActionTime createTime;
    private Set<TransportationProduct> products = new HashSet<>();
    private Address address;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = DATE)
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    @OneToOne
    @JoinColumn(name = ADDRESS)
    public Address getAddress() {
        return address;
    }
    public void setAddress(Address address) {
        this.address = address;
    }

    @OneToOne
    @JoinColumn(name = VEHICLE)
    public Vehicle getVehicle() {
        return vehicle;
    }
    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    @OneToOne
    @JoinColumn(name = TRAILER)
    public Trailer getTrailer() {
        return trailer;
    }
    public void setTrailer(Trailer trailer) {
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
    @Column(name = TRANSPORTER)
    public String getTransporterValue() {
        return transporterValue;
    }
    void setTransporterValue(String transporterValue) {
        this.transporterValue = transporterValue;
    }

    @OneToOne
    @JoinColumn(name = DRIVER)
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
    void setDriverLicense(String driverLicense) {
        this.driverLicense = driverLicense;
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
    @JoinColumn(name = MANAGER)
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
    @JoinColumn(name = DONE)
    public boolean isDone() {
        return done;
    }
    public void setDone(boolean done) {
        this.done = done;
    }

    @Basic
    @Column(name = ARCHIVE)
    public boolean isArchive() {
        return archive;
    }
    public void setArchive(boolean archive) {
        this.archive = archive;
    }


    @Transient
    public boolean any(){
        boolean any = false;
        for (TransportationProduct product : products){
            if (product.getWeight() != null){
                any = true;
            }
            if(product.getSunAnalyses() != null){
                any = true;
            }
            if (product.getOilAnalyses() != null){
                any = true;
            }
        }

        return timeIn != null || timeOut != null || any;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Basic
    @Column(name = UID)
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

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "transportation", cascade = CascadeType.ALL)
    public Set<TransportationProduct> getProducts() {
        return products;
    }
    public void setProducts(Set<TransportationProduct> products) {
        this.products = products;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = CUSTOMER)
    public TransportCustomer getCustomer() {
        return customer;
    }
    public void setCustomer(TransportCustomer customer) {
        this.customer = customer;
    }

    @Override
    public JSONObject toJson(int level) {
        JSONObject json = pool.getObject();

        json.put(ID, id);
        json.put(DATE, date.toString());
        json.put(CUSTOMER, customer.toString());
        if (driver != null) {
            json.put(DRIVER, driver.toJson(level));
            json.put(LICENSE, driverLicense);
        }
        if (vehicle != null) {
            json.put(VEHICLE, vehicle.toJson(level));
        }
        if (trailer != null){
            json.put(TRAILER, trailer.toJson(level));
        }
        if (transporter != null) {
            json.put(TRANSPORTER, transporter.toShortJson());
        }
        if (timeRegistration != null) {
            json.put(REGISTRATION, timeRegistration.toShortJson());
        }
        if (timeIn != null) {
            json.put(TIME_IN, timeIn.toShortJson());
        }
        if (timeOut != null) {
            json.put(TIME_OUT, timeOut.toShortJson());
        }
        json.put(GROSS, gross());
        json.put(TARE, tare());

        json.put(PRODUCTS, products(level));

        json.put(NOTES, notes(level));
        json.put(NOTE_MAP, noteMap(level));
        json.put(ANY, any());
        json.put(ARCHIVE, archive);
        json.put(DONE, done);

        if(manager != null) {
            json.put(MANAGER, manager.toShortJson());
        }
        json.put(CREATE, createTime.toJson(level));
        return json;
    }

    private JSONArray notes(int level) {
        JSONArray array = new JSONArray();
        for (DocumentNote note : notes){
            array.add(note.toJson(level));
        }
        return array;
    }

    private JSONObject noteMap(int level) {
        HashMap<Object, LinkedList<DocumentNote>> noteMap = new HashMap<>();
        if (notes != null){
            NoteUtil.sort(notes);
            for (DocumentNote note : notes){
                final Worker creator = note.getCreator();
                Object key = null;
                if (creator != null){
                    key = creator.getValue();
                }

                if (!noteMap.containsKey(key)){
                    noteMap.put(key, new LinkedList<>());
                }
                noteMap.get(key).add(note);
            }
        }

        final JSONObject object = new JSONObject();
        for (Map.Entry<Object, LinkedList<DocumentNote>> entry : noteMap.entrySet()){
            final JSONArray array = new JSONArray();
            final LinkedList<DocumentNote> value = entry.getValue();
            for (DocumentNote note : value){
                array.add(note.toJson(level));
            }

            object.put(entry.getKey(), array);
        }
        return object;
    }

    private JSONArray products(int level) {
        JSONArray array = new JSONArray();
        for (TransportationProduct product : products){
            array.add(product.toJson(level));
        }
        return array;
    }

    private float tare() {
        float tare = 0;
        for (TransportationProduct product : products){
            final Weight weight = product.getWeight();
            if (weight != null){
                tare += weight.getTara();
            }
        }
        return tare;
    }

    private float gross() {
        float gross = 0;
        for (TransportationProduct product : products){
            final Weight weight = product.getWeight();
            if (weight != null){
                gross += weight.getBrutto();
            }
        }

        return gross;
    }
}
