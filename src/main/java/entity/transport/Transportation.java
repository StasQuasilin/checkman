package entity.transport;

import entity.Worker;
import entity.documents.DocumentOrganisation;
import entity.documents.IDocument;
import entity.laboratory.transportation.CakeTransportationAnalyses;
import entity.laboratory.transportation.OilTransportationAnalyses;
import entity.laboratory.transportation.SunTransportationAnalyses;
import entity.seals.Seal;
import entity.weight.Weight;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@Entity
@Table(name = "transportations")
public class Transportation extends IDocument{
//    private int id;
//    private Date date;

    private Vehicle vehicle;
    private Driver driver;
    private DocumentOrganisation documentOrganisation;
    private ActionTime timeIn;
    private ActionTime timeOut;
    private Worker creator;
    private Set<Weight> weights;
    private Set<SunTransportationAnalyses> sunAnalyses;
    private Set<OilTransportationAnalyses> oilAnalyses;
    private Set<CakeTransportationAnalyses> cakeAnalyses;
    private Set<Seal> seals;
    private boolean archive;

    @Override
    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }
    @Override
    public void setId(int id) {
        this.id = id;
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
    @JoinColumn(name = "document_organisation")
    public DocumentOrganisation getDocumentOrganisation() {
        return documentOrganisation;
    }
    public void setDocumentOrganisation(DocumentOrganisation documentOrganisation) {
        this.documentOrganisation = documentOrganisation;
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
    @JoinColumn(name = "creator")
    public Worker getCreator() {
        return creator;
    }
    public void setCreator(Worker creator) {
        this.creator = creator;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "transportation", cascade = CascadeType.ALL)
    public Set<Weight> getWeights() {
        return weights;
    }
    public void setWeights(Set<Weight> weights) {
        this.weights = weights;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "transportation", cascade = CascadeType.ALL)
    public Set<SunTransportationAnalyses> getSunAnalyses() {
        return sunAnalyses;
    }
    public void setSunAnalyses(Set<SunTransportationAnalyses> sunAnalyses) {
        this.sunAnalyses = sunAnalyses;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "transportation", cascade = CascadeType.ALL)
    public Set<OilTransportationAnalyses> getOilAnalyses() {
        return oilAnalyses;
    }
    public void setOilAnalyses(Set<OilTransportationAnalyses> oilAnalyses) {
        this.oilAnalyses = oilAnalyses;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "transportation", cascade = CascadeType.ALL)
    public Set<CakeTransportationAnalyses> getCakeAnalyses() {
        return cakeAnalyses;
    }
    public void setCakeAnalyses(Set<CakeTransportationAnalyses> cakeAnalyses) {
        this.cakeAnalyses = cakeAnalyses;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "transportation", cascade = CascadeType.ALL)
    public Set<Seal> getSeals() {
        return seals;
    }
    public void setSeals(Set<Seal> seals) {
        this.seals = seals;
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
            weights.size() > 0 ||
            sunAnalyses.size() > 0 ||
            oilAnalyses.size() > 0 ||
            cakeAnalyses.size() > 0;
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
        hash = 31 * documentOrganisation.hashCode() + hash;
        if (timeIn != null) {
            hash = 31 * timeIn.hashCode() + hash;
        }
        if (timeOut != null){
            hash = 31 * timeOut.hashCode() + hash;
        }

        for (Weight w : weights){
            hash = 31 * w.hashCode() + hash;
        }

        for (SunTransportationAnalyses a : sunAnalyses){
            hash = 31 * a.hashCode() + hash;
        }

        for (OilTransportationAnalyses a : oilAnalyses){
            hash = 31 * a.hashCode() + hash;
        }

        for (CakeTransportationAnalyses a : cakeAnalyses){
            hash = 31 * a.hashCode() + hash;
        }

        for (Seal seal : seals){
            hash = 31 * seal.getNumber().hashCode() + hash;
        }

        hash = 31 * Boolean.hashCode(anyAction()) + hash;
        hash = 31 * Boolean.hashCode(archive) + hash;
        return hash;
    }

    @Transient
    public float getWeight() {
        float netto = 0;
        for (Weight weight : weights) {
            netto += weight.getNetto();
        }
        return netto;
    }
}
