package entity;

import entity.documents.Shipper;
import entity.organisations.Organisation;
import entity.products.Product;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by szpt_user045 on 25.02.2020.
 */
@Entity
@Table(name = "statistic_entry")
public class StatisticEntry {
    private int id;
    private int document;
    private Organisation organisation;
    private Date date;
    private Product product;
    private Shipper shipper;
    private float amount;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "document")
    public int getDocument() {
        return document;
    }
    public void setDocument(int document) {
        this.document = document;
    }

    @OneToOne
    @JoinColumn(name = "organisation")
    public Organisation getOrganisation() {
        return organisation;
    }
    public void setOrganisation(Organisation organisation) {
        this.organisation = organisation;
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
    @JoinColumn(name = "product")
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }

    @OneToOne
    @JoinColumn(name = "shipper")
    public Shipper getShipper() {
        return shipper;
    }
    public void setShipper(Shipper shipper) {
        this.shipper = shipper;
    }

    @Basic
    @Column(name = "amount")
    public float getAmount() {
        return amount;
    }
    public void setAmount(float amount) {
        this.amount = amount;
    }
}
