package entity.documents;

import entity.Worker;
import entity.organisations.Organisation;
import org.glassfish.jersey.server.BackgroundScheduler;
import utils.DocumentUIDGenerator;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@Entity
@Table(name = "deals")
public class Deal extends IDocument{
//    private int id;
//    private Date date;

    private Date dateTo;
    private Organisation organisation;
    private Worker creator;
    private String uid;

    public Deal() {
//        uid = DocumentUIDGenerator.generateUID();
    }

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

    @Basic
    @Column(name = "date_to")
    public Date getDateTo() {
        return dateTo;
    }
    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    @OneToOne
    @JoinColumn(name = "organisation")
    public Organisation getOrganisation() {
        return organisation;
    }
    public void setOrganisation(Organisation organisation) {
        this.organisation = organisation;
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
    @Column(name = "uid")
    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }
}
