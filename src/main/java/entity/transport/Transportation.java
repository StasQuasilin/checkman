package entity.transport;

import entity.DealType;
import entity.Worker;
import entity.documents.Shipper;
import entity.laboratory.CakeAnalyses;
import entity.laboratory.OilAnalyses;
import entity.laboratory.SunAnalyses;
import entity.organisations.Organisation;
import entity.products.Product;
import entity.seals.Seal;
import entity.weight.Weight;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@Entity
@Table(name = "transportations")
public class Transportation {
    private int id;
    private Date date;
    private Organisation counterparty;
    private DealType type;
    private Vehicle vehicle;
    private Driver driver;
    private Shipper shipper;
    private ActionTime timeIn;
    private ActionTime timeOut;
    private Product product;
    private Weight weight;
    private SunAnalyses sunAnalyses;
    private OilAnalyses oilAnalyses;
    private CakeAnalyses cakeAnalyses;
    private Set<Seal> seals;
    private Worker creator;
    private Set<TransportationNote> notes;
    private boolean archive;
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
    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
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
    @JoinColumn(name = "shipper")
    public Shipper getShipper() {
        return shipper;
    }
    public void setShipper(Shipper shipper) {
        this.shipper = shipper;
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
    public CakeAnalyses getCakeAnalyses() {
        return cakeAnalyses;
    }
    public void setCakeAnalyses(CakeAnalyses cakeAnalyses) {
        this.cakeAnalyses = cakeAnalyses;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "cargo", cascade = CascadeType.ALL)
    public Set<Seal> getSeals() {
        return seals;
    }
    public void setSeals(Set<Seal> seals) {
        this.seals = seals;
    }

    @OneToOne
    @JoinColumn(name = "creator")
    public Worker getCreator() {
        return creator;
    }
    public void setCreator(Worker creator) {
        this.creator = creator;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "transportation", cascade = CascadeType.ALL)
    public Set<TransportationNote> getNotes() {
        return notes;
    }
    public void setNotes(Set<TransportationNote> notes) {
        this.notes = notes;
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
            timeOut != null ||
            weight != null ||
            sunAnalyses != null ||
            oilAnalyses != null ||
            cakeAnalyses != null;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        if (vehicle != null) {
            hash = 31 * vehicle.hashCode() + hash;
        }
        if (driver != null) {
            hash = 31 * driver.hashCode() + hash;
        }
        hash = 31 * shipper.hashCode() + hash;
        if (timeIn != null) {
            hash = 31 * timeIn.hashCode() + hash;
        }
        if (timeOut != null){
            hash = 31 * timeOut.hashCode() + hash;
        }

        if (weight != null) {
            hash = 31 * weight.hashCode() + hash;
        }

        if (sunAnalyses != null) {
            hash = 31 * sunAnalyses.hashCode() + hash;
        }

        if (oilAnalyses != null) {
            hash = 31 * oilAnalyses.hashCode() + hash;
        }

        if (cakeAnalyses != null) {
            hash = 31 * cakeAnalyses.hashCode() + hash;
        }
        if (seals != null) {
            for (Seal seal : seals) {
                hash = 31 * seal.getNumber().hashCode() + hash;
            }
        }
        if (notes != null) {
            for (TransportationNote note : notes) {
                hash = 31 * note.getNote().hashCode() + hash;
            }
        }

        hash = 31 * Boolean.hashCode(archive) + hash;

        return hash;
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
