package entity.documents;

import entity.transport.TransportCustomer;
import entity.transport.Transportation;
import org.glassfish.jersey.server.BackgroundScheduler;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@Entity
@Table(name = "load_plans")
public class LoadPlan extends IDocument{
//    private int id;
//    private Date date;

    private Deal deal;
    private TransportCustomer customer;
    private Transportation transportation;
    private DocumentOrganisation documentOrganisation;


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
    @JoinColumn(name = "deal")
    public Deal getDeal() {
        return deal;
    }
    public void setDeal(Deal deal) {
        this.deal = deal;
    }

    @Basic
    @Column(name = "transport_customer")
    public TransportCustomer getCustomer() {
        return customer;
    }
    public void setCustomer(TransportCustomer customer) {
        this.customer = customer;
    }

    @OneToOne
    @JoinColumn(name = "transportation")
    public Transportation getTransportation() {
        return transportation;
    }
    public void setTransportation(Transportation transportation) {
        this.transportation = transportation;
    }

    @OneToOne
    @JoinColumn(name = "document_organisation")
    public DocumentOrganisation getDocumentOrganisation() {
        return documentOrganisation;
    }
    public void setDocumentOrganisation(DocumentOrganisation documentOrganisation) {
        this.documentOrganisation = documentOrganisation;
    }
}
