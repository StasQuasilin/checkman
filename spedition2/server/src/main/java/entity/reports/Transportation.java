package entity.reports;

import entity.references.Driver;
import entity.references.Product;

import javax.persistence.*;

@Entity
@Table(name = "transportations")
public class Transportation {
    private int id;
    private Report report;
    private Driver driver;
    private Product product;
    private Weight ownWeight;
    private Weight foreignWeight;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "_id")
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @OneToOne
    @JoinColumn(name = "_report")
    public Report getReport() {
        return report;
    }
    public void setReport(Report report) {
        this.report = report;
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
    @JoinColumn(name = "_product")
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }

    @OneToOne
    @JoinColumn(name = "_own_weight")
    public Weight getOwnWeight() {
        return ownWeight;
    }
    public void setOwnWeight(Weight ownWeight) {
        this.ownWeight = ownWeight;
    }

    @OneToOne
    @JoinColumn(name = "_foreignWeight")
    public Weight getForeignWeight() {
        return foreignWeight;
    }
    public void setForeignWeight(Weight foreignWeight) {
        this.foreignWeight = foreignWeight;
    }
}
