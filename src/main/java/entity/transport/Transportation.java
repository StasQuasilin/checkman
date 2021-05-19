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
import utils.U;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
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
    private Set<TransportationProduct> products;
    private Address address;

    @Deprecated
    private SunAnalyses sunAnalyses;
    @Deprecated
    private OilAnalyses oilAnalyses;
    @Deprecated
    private MealAnalyses mealAnalyses;
    @Deprecated
    private Weight weight;
    @Deprecated
    private float amount;
    @Deprecated
    private Deal deal;
    @Deprecated
    private Shipper shipper;
    @Deprecated
    private Product product;

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
    @JoinColumn(name = DEAL)
    @Deprecated
    public Deal getDeal() {
        return deal;
    }
    @Deprecated
    public void setDeal(Deal deal) {
        this.deal = deal;
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
    @JoinColumn(name = SHIPPER)
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
    @JoinColumn(name = PRODUCT)
    @Deprecated
    public Product getProduct() {
        return product;
    }
    @Deprecated
    public void setProduct(Product product) {
        this.product = product;
    }

    @OneToOne
    @JoinColumn(name = WEIGHT)
    @Deprecated
    public Weight getWeight() {
        return weight;
    }
    @Deprecated
    public void setWeight(Weight weight) {
        this.weight = weight;
    }

    @OneToOne
    @JoinColumn(name = "sun_analyses")
    @Deprecated
    public SunAnalyses getSunAnalyses() {
        return sunAnalyses;
    }
    @Deprecated
    public void setSunAnalyses(SunAnalyses sunAnalyse) {
        this.sunAnalyses = sunAnalyse;
    }

    @OneToOne
    @JoinColumn(name = "oil_analyses")
    @Deprecated
    public OilAnalyses getOilAnalyses() {
        return oilAnalyses;
    }
    @Deprecated
    public void setOilAnalyses(OilAnalyses oilAnalyses) {
        this.oilAnalyses = oilAnalyses;
    }

    @OneToOne
    @JoinColumn(name = "meal_analyses")
    @Deprecated
    public MealAnalyses getMealAnalyses() {
        return mealAnalyses;
    }
    @Deprecated
    public void setMealAnalyses(MealAnalyses mealAnalyses) {
        this.mealAnalyses = mealAnalyses;
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
    public Organisation getCounterparty(){
        return deal.getOrganisation();
    }


    @Transient
    public boolean any(){
        return timeIn != null || timeOut != null || weight != null ||
                sunAnalyses != null || oilAnalyses != null || mealAnalyses != null;
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

    @Basic
    @Column(name = AMOUNT)
    @Deprecated
    public float getAmount() {
        return amount;
    }
    @Deprecated
    public void setAmount(float amount) {
        this.amount = amount;
    }

    @Override
    public JSONObject toJson(int level) {
        JSONObject json = pool.getObject();

        json.put(ID, id);

        json.put(TYPE, deal.getType().toString());
        json.put(DATE, date.toString());
        json.put(CUSTOMER, customer.toString());
        if (driver != null) {
            json.put(DRIVER, driver.toJson());
            json.put(LICENSE, driverLicense);
        }
        if (vehicle != null) {
            json.put(VEHICLE, vehicle.toJson());
        }
        if (trailer != null){
            json.put(TRAILER, trailer.toJson());
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
        json.put(Constants.GROSS, gross());
        json.put(Constants.TARE, tare());

        json.put(PRODUCTS, products(level));

        if (U.exist(deal.getNumber())){
            json.put(CONTRACT_NUMBER, deal.getNumber());
            json.put(DEAL_DATE, deal.getDate().toString());
        }

//        json.put(PRODUCT, deal.getProduct().toJson());
//        json.put(PLAN, amount);
//        json.put(UNIT, deal.getUnit().getName());
//        json.put(PRICE, deal.getPrice());
//        json.put(COUNTERPARTY, deal.getOrganisation().toShortJson());
//        json.put(SHIPPER, deal.getShipper().getValue());
//        json.put(ANALYSES, analyses());
        json.put(NOTES, notes.stream().map(documentNote -> documentNote.toJson()).collect(Collectors.toList()));
        json.put(ANY, any());
        json.put(ARCHIVE, archive);
        json.put(DONE, done);
        json.put(MANAGER, manager.toShortJson());
        json.put(CREATE, createTime.toJson());
        return json;
    }

    private JSONArray products(int level) {
        JSONArray array = new JSONArray();
        if (products.size() > 0){
            for (TransportationProduct product : products){
                array.add(product.toJson(level));
            }
        } else {
            for (int i = 0; i < 1; i++) {
                final JSONObject object = new JSONObject();
                object.put(ID, id);
                final Product product = getDeal().getProduct();
                object.put(PRODUCT_ID, product.getId());
                object.put(PRODUCT_NAME, product.getName());
                object.put(PRODUCT, product.toShortJson());
                object.put(QUANTITY, deal.getQuantity());
                object.put(AMOUNT, amount);
                object.put(DEAL, deal.getId());
                object.put(DEAL_PRODUCT, deal.getId());
                object.put(UNIT, deal.getUnit().getName());

                object.put(COUNTERPARTY, deal.getOrganisation().toShortJson());
                object.put(SHIPPER, deal.getShipper().getValue());
                object.put(SHIPPER_ID, deal.getShipper().getId());
                object.put(SHIPPER_NAME, deal.getShipper().getValue());
                if (level == OnSubscribeHandler.NO_ONE || level == OnSubscribeHandler.NO_PRICE){
                    object.put(PRICE, 0);
                } else {
                    object.put(PRICE, deal.getPrice());
                }
                object.put(PRICE, deal.getPrice());
                object.put(ANALYSES, analyses(level));
                if (address != null) {
                    object.put(ADDRESS, address.toJson());
                }
                if (weight != null) {
                    object.put(WEIGHT, weight.toJson());
                }
                array.add(object);
            }
        }
        return array;
    }

    private float tare() {
        if (weight != null){
            return weight.getTara();
        }
        return 0;
    }

    private float gross() {
        if (weight != null){
            return weight.getBrutto();
        }
        return 0;
    }

    private JSONObject analyses(int level) {
        JSONObject json = pool.getObject();
        if (level != OnSubscribeHandler.NO_ONE && level != OnSubscribeHandler.NO_ANALYSES){
            if (sunAnalyses != null) {
                json.put(SUN, sunAnalyses.toJson());
            }
            if (oilAnalyses != null) {
                json.put(OIL, oilAnalyses.toJson());
            }
        }
        return json;
    }

    @Transient
    public DealType getType() {
        return deal.getType();
    }
}
