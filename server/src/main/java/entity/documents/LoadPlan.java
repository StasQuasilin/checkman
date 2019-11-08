package entity.documents;

import entity.transport.TransportCustomer;
import entity.transport.Transportation;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by szpt_user045 on 11.03.2019.
 */
@Entity
@Table(name = "load_plans")
public class LoadPlan{
    private int id;
    private Date date;
    private Deal deal;
    private float plan;
    private TransportCustomer customer;
    private Transportation transportation;
    private Shipper shipper;
    private boolean canceled;
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
    @JoinColumn(name = "deal")
    public Deal getDeal() {
        return deal;
    }
    public void setDeal(Deal deal) {
        this.deal = deal;
    }

    @Basic
    @Column(name = "plan")
    public float getPlan() {
        return plan;
    }
    public void setPlan(float plan) {
        this.plan = plan;
    }

    @Enumerated(EnumType.STRING)
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
    public Shipper getShipper() {
        return shipper;
    }
    public void setShipper(Shipper shipper) {
        this.shipper = shipper;
    }

    @Basic
    @Column(name = "canceled")
    public boolean isCanceled() {
        return canceled;
    }
    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
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
    public int hashCode() {
        int hash = 7;
        hash = 31 * id + hash;
        hash = 31 * date.hashCode() + hash;
        hash = 31 * Float.hashCode(plan) + hash;
        hash = 31 * customer.hashCode() + hash;
        hash = 31 * transportation.hashCode() + hash;
        hash = 31 * shipper.hashCode() + hash;
        return hash;
    }
}
