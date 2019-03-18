package entity.transport;

import entity.Worker;
import entity.documents.DocumentOrganisation;
import entity.documents.IDocument;

import javax.persistence.*;
import java.sql.Date;

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

    @Override
    @Basic
    @Column(name = "date")
    public Date getDate() {
        return date;
    }
    @Override
    public void setDate(Date date) {
        this.date = date;
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

    @Basic
    @Column(name = "archive")
    public boolean isArchive() {
        return archive;
    }
    public void setArchive(boolean archive) {
        this.archive = archive;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * date.hashCode() + hash;
        hash = 31 * vehicle.hashCode() + hash;
        hash = 31 * driver.hashCode() + hash;
        hash = 31 * documentOrganisation.hashCode() + hash;
        if (timeIn != null) {
            hash = 31 * timeIn.hashCode() + hash;
        }
        if (timeOut != null){
            hash = 31 * timeOut.hashCode() + hash;
        }
        hash = 31 * Boolean.hashCode(archive) + hash;
        return hash;
    }
}
